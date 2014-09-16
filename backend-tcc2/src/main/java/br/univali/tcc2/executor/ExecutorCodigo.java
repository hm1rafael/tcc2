package br.univali.tcc2.executor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import tcc2.desafio.annotations.Challenge;
import tcc2.desafio.annotations.ChallengeClass;
import tcc2.desafio.annotations.Solution;
import tcc2.desafio.annotations.TargetImplementation;
import tcc2.desafio.exception.DesafioFalhouException;
import br.univali.tcc2.Classe;
import br.univali.tcc2.CodigoFonte;
import br.univali.tcc2.compiler.VerificadorCodigo;

@Component
public class ExecutorCodigo {
	
	public boolean executarCodigoUsuario(List<Classe> classes, int numeroValidacoesASerRealizadas, CodigoFonte codigoFonte) {
		Class clazzExecucao = null;
		Class clazzUtils = null;
		for (Classe classe : classes) {
			Class clazzAux = classe.getClazz();
			if ("Execucao".equals(clazzAux.getSimpleName())) {
				clazzExecucao = clazzAux;
				continue;
			}
			if ("Utils".equals(clazzAux.getSimpleName())) {
				clazzUtils = clazzAux;
			}
		}
		if (clazzExecucao == null) {
			throw new IllegalStateException("Não foi encontrada a classe de execução padrão do programa, verifique se você não alterou o nome da classe Execucao");
		}
		try {
			escreverArquivo(codigoFonte.getEntrada(), true, clazzUtils);
			escreverArquivo(codigoFonte.getSaida(), false, clazzUtils);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Field[] declaredFields = clazzUtils.getDeclaredFields();
		return verificarExecucaoCorreta(clazzExecucao,clazzUtils, numeroValidacoesASerRealizadas);
	}

	private void escreverArquivo(String arquivo, boolean entrada, Class clazzUtilz)
			throws FileNotFoundException, IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ByteArrayInputStream bais= new ByteArrayInputStream(arquivo.getBytes());
		inserirScanners(entrada, clazzUtilz, bais);
	}

	private void inserirScanners(boolean entrada, Class clazzUtilz,
			ByteArrayInputStream bais) throws NoSuchFieldException,
			IllegalAccessException {
		if (entrada) {
			Field field = clazzUtilz.getDeclaredField("scannerEntrada");
			field.setAccessible(true);
			field.set(null, new Scanner(bais));
		} else {
			Field field = clazzUtilz.getDeclaredField("scannerSaida");
			field.setAccessible(true);
			field.set(null, new Scanner(bais));
		}
	}

	private boolean verificarExecucaoCorreta(Class clazzExecucao,Class classUtils, int numeroValidacoesASerRealizadas) {
		try {
	  		Object classeExecucaoPrincipal = clazzExecucao.newInstance();
			Method metodoInicial = ReflectionUtils.findMethod(clazzExecucao, "processarEntrada");
			metodoInicial.invoke(classeExecucaoPrincipal);
			Field campoTotalValidacoes = ReflectionUtils.findField(classUtils, "validacoes");
			campoTotalValidacoes.setAccessible(true);
			Object totalValidacoes = campoTotalValidacoes.get(null);
			return (Integer)totalValidacoes == numeroValidacoesASerRealizadas;
		} catch (IllegalStateException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Deprecated
	public boolean executarCodigo(List<Classe> classes) {
		try {
			ModeloExecutor modeloExecutor = carregarModeloExecutor(classes);
			Class classeDesafio = modeloExecutor.getClasseDesafio();
			Object newInstance = classeDesafio.newInstance();
			Field[] fields = classeDesafio.getDeclaredFields();
			for (Field field : fields) {
				if(field.isAnnotationPresent(Solution.class)) {
					field.setAccessible(true);
					field.set(newInstance, modeloExecutor.getSolucao().newInstance());
					break;
				}
			}
			Method[] methods = classeDesafio.getMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(Challenge.class)) {
					method.invoke(newInstance);
				}
			}
			return true;
		} catch (DesafioFalhouException e) {
			return false;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
		
	}

	private ModeloExecutor carregarModeloExecutor(List<Classe> classes) {
		ModeloExecutor modeloExecutor = new ModeloExecutor();
		for (Classe classe : classes) {
			Class clazz = classe.getClazz();
			if (modeloExecutor.getClasseDesafio() == null) {
				verificarSeAClasseDeDesafio(modeloExecutor, classe, clazz);
			}
			boolean annotationPresent = clazz.isAnnotationPresent(ChallengeClass.class);
			if (annotationPresent) {
				modeloExecutor.setClasseDesafio(classe.getClazz());
			}
		}
		return modeloExecutor;
	}

	private void verificarSeAClasseDeDesafio(ModeloExecutor modeloExecutor, Classe classe, Class clazz) {
		Class[] interfaces = clazz.getInterfaces();
		for (Class interfaceClazz : interfaces) {
			boolean annotationPresent = interfaceClazz.isAnnotationPresent(TargetImplementation.class);
			if (annotationPresent) {
				modeloExecutor.setSolucao(classe.getClazz());
				break;
			}
		}
	}
	
}

class ModeloExecutor {
	
	private Class classeDesafio;
	private Class solucao;
	
	public Class getClasseDesafio() {
		return classeDesafio;
	}
	
	public Class getSolucao() {
		return solucao;
	}
	
	public void setClasseDesafio(Class classeDesafio) {
		this.classeDesafio = classeDesafio;
	}
	
	public void setSolucao(Class solucao) {
		this.solucao = solucao;
	}
	
}
