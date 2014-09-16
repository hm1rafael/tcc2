package br.univali.tcc2.compiler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.jci.compilers.CompilationResult;
import org.apache.commons.jci.compilers.JavaCompiler;
import org.apache.commons.jci.compilers.JavaCompilerFactory;
import org.apache.commons.jci.compilers.JavaCompilerSettings;
import org.apache.commons.jci.problems.CompilationProblem;
import org.apache.commons.jci.readers.MemoryResourceReader;
import org.apache.commons.jci.stores.MemoryResourceStore;
import org.apache.commons.jci.stores.ResourceStore;
import org.apache.commons.jci.stores.ResourceStoreClassLoader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.univali.tcc2.ArquivoCodigoFonte;
import br.univali.tcc2.Classe;
import br.univali.tcc2.DesafioUsuario;
import br.univali.tcc2.Erro;
import br.univali.tcc2.Erros;
import br.univali.tcc2.PosicaoErro;
import br.univali.tcc2.configuration.InstrumentationHook;
import br.univali.tcc2.exception.ErroCompilacaoException;

/**
 * Classe com o papel de verificar os dados do desafio do usuario, compilar o
 * codigo e caso haja algum erro, agrupar os erros para demonstrar na tela (seja
 * web ou qualquer outro provider)
 * 
 * @author rafael
 * 
 */
@Component
public class VerificadorCodigo {

	private static final String JAVA_VERSION = "1.6";
	private static final String ECLIPSE = "eclipse";
	private JavaCompilerSettings jcs = new JavaCompilerSettings();

	@PostConstruct
	public void criarSettings() {
		jcs.setSourceVersion(JAVA_VERSION);
		jcs.setTargetVersion(JAVA_VERSION);
	}

	public List<Classe> compilarCodigo(DesafioUsuario desafioUsuario) {
		JavaCompiler javaCompiler = new JavaCompilerFactory().createCompiler(ECLIPSE);
		ResourceStore resourceStore = new MemoryResourceStore();
		MemoryResourceReader leitorDeCodigo = new MemoryResourceReader();
		adicionarClassesAoLeitor(desafioUsuario, leitorDeCodigo);
		String[] listaDeClasses = leitorDeCodigo.list();
		long currentTimeMillis = System.currentTimeMillis();
		URLClassLoader urlClassLoaderWithJar = loadJarClasses(desafioUsuario,currentTimeMillis);
		CompilationResult resultadoDaCompilacao = javaCompiler.compile(listaDeClasses, leitorDeCodigo, resourceStore, urlClassLoaderWithJar, this.jcs);
		assertCompilacaoSemProblemas(desafioUsuario, resultadoDaCompilacao);
		return carregarClasses(resourceStore, urlClassLoaderWithJar, listaDeClasses);
	}

	private URLClassLoader loadJarClasses(DesafioUsuario desafioUsuario,
			long currentTimeMillis) {
		List<URL> urls = new ArrayList<URL>();
		List<String> clazzNames = new ArrayList<String>();
		try {
			URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls
					.toArray(new URL[urls.size()]));
			for (String string : clazzNames) {
				urlClassLoader.loadClass(string);
			}
			return urlClassLoader;
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	private List<Classe> carregarClasses(ResourceStore resourceStore,
			ClassLoader parentClassLoader, String[] listaDeClasses) {
		List<Classe> classes = new ArrayList<Classe>();
		ResourceStoreClassLoader bacl = new ResourceStoreClassLoader(
				parentClassLoader, new ResourceStore[] { resourceStore });
		try {
			for (String resourceName : listaDeClasses) {
				byte[] resource = resourceStore.read(StringUtils.replace(
						resourceName, ".java", ".class"));
				ClassParser classParser = new ClassParser(
						new ByteArrayInputStream(resource), resourceName);
				JavaClass parse = classParser.parse();
				Class<?> clazz = bacl.loadClass(FilenameUtils.removeExtension(
						resourceName).replace('/', '.'));
				classes.add(new Classe(parse, clazz));
			}
			List<Class> loadedClasses = InstrumentationHook
					.getLoadedClasses(parentClassLoader);
			for (Class class1 : loadedClasses) {
				classes.add(new Classe(class1));
			}
		} catch (ClassFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return classes;
	}

	private void assertCompilacaoSemProblemas(DesafioUsuario desafioUsuario,
			CompilationResult resultadoDaCompilacao) {
		if (resultadoDaCompilacao.getErrors().length != 0) {
			Erros erros = parseProblems(resultadoDaCompilacao.getErrors());
			desafioUsuario.setErros(erros);
			throw new ErroCompilacaoException();
		}
	}

	private void adicionarClassesAoLeitor(DesafioUsuario desafioUsuario,
			MemoryResourceReader leitorDeCodigo) {
		for (ArquivoCodigoFonte arquivoCodigoFonte : desafioUsuario.getCodigo()) {
			byte[] arquivoCodigoFonteBytes = arquivoCodigoFonte
					.getArquivoCodigoFonte();
			String string = new String(arquivoCodigoFonteBytes);
			leitorDeCodigo.add(arquivoCodigoFonte.getNome(),
					arquivoCodigoFonteBytes);
		}
	}

	private Erros parseProblems(CompilationProblem[] errors) {
		Erros erros = new Erros();
		for (CompilationProblem compilationProblem : errors) {
			Erro erro = criarErroAPartirDeCompilationProblem(compilationProblem);
			erros.adicionarErro(erro);
		}
		return erros;
	}

	private Erro criarErroAPartirDeCompilationProblem(
			CompilationProblem compilationProblem) {
		Erro erro = new Erro();
		erro.setNomeArquivo(compilationProblem.getFileName());
		erro.setPosicaoErro(new PosicaoErro(compilationProblem.getStartLine(),
				compilationProblem.getEndLine(), compilationProblem
						.getStartColumn(), compilationProblem.getEndColumn()));
		return erro;
	}

}
