package tcc2.portal.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import tcc2.desafio.Resultado;
import tcc2.portal.domain.Desafio;
import tcc2.portal.domain.DesafioMetricas;
import tcc2.portal.domain.Dificuldade;
import tcc2.portal.domain.UsuarioDesafio;
import tcc2.portal.domain.UsuarioDesafioPK;
import tcc2.portal.provider.UserInfuserProvider;
import tcc2.portal.repositorio.DesafioMetricasRepositorio;
import tcc2.portal.repositorio.UsuarioDesafioRepository;

import com.google.gson.Gson;

@RequestMapping("/desafios")
@Controller
@RooWebScaffold(path = "desafios", formBackingObject = Desafio.class)
public class DesafioController {
	
	private Gson gson = new Gson();
	
	@Autowired
	private UsuarioDesafioRepository usuarioDesafioRepository;
	
	@Autowired
	private DesafioMetricasRepositorio desafioMetricasRepositorio;
	
	@Autowired
	private UserInfuserProvider userInfuserProvider;
	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Desafio desafio, BindingResult bindingResult,@RequestParam("metricasSelecionadas") List<String> metricas,
    			@RequestParam("inputFile") MultipartFile input, 
    			@RequestParam("outputFile") MultipartFile output,
    			@RequestParam("explicacaoDesafio") MultipartFile explicao,
    			Model uiModel, 
    			HttpServletRequest httpServletRequest, @RequestParam Map<String, String> valorMetricas) throws IOException {			
		desafio.setInputFile(input.getBytes());
		desafio.setOutputFile(output.getBytes());
		desafio.setExplicacaoDesafio(explicao.getBytes());
		int qtValidacoes = IOUtils.readLines(output.getInputStream()).size();
		desafio.setQuantidadeDeValidacoes(qtValidacoes);
		desafio.persist();
		persistirMetricas(desafio, metricas, valorMetricas);
        return "redirect:/desafios/" + encodeUrlPathSegment(desafio.getId().toString(), httpServletRequest);
    }
 

	private void persistirMetricas(Desafio desafio, List<String> metricas, Map<String, String> valorMetricas) {
		for (String metrica : metricas) {
			DesafioMetricas desafioMetricas = new DesafioMetricas(metrica);
			desafioMetricas.setIdDesafio(desafio.getId());
			String valorMetrica = valorMetricas.get(String.format("%svalue",metrica));
			desafioMetricas.setValorMetrica(Double.valueOf(valorMetrica));
			desafioMetricas.persist();
		}
	}
	
	
	@RequestMapping(produces = "text/html", value ="/update")
    public String update(@Valid Desafio desafio, BindingResult bindingResult, 
    		@RequestParam(required = false, value = "metricasSelecionadas") List<String> metricas,
			@RequestParam(required = false, value = "inputFile") MultipartFile input, 
			@RequestParam(required = false, value = "outputFile") MultipartFile output,
			@RequestParam(required = false, value = "explicacaoDesafio") MultipartFile explicao, Model uiModel, HttpServletRequest httpServletRequest, Map<String, String> valorMetricas) throws IllegalAccessException, InvocationTargetException, IOException {
        Desafio desafioDaBase = Desafio.findDesafio(desafio.getId());
        if (CollectionUtils.isNotEmpty(metricas)) {
        	DesafioMetricas desafioMetricas = new DesafioMetricas();
        	desafioMetricas.setId(desafio.getId());
        	desafioMetricas.remove();
        	persistirMetricas(desafioDaBase, metricas, valorMetricas);
        }
        try {
        	desafio.setInputFile(input.getBytes());
        } catch (Exception e) {
        	// ignora
        }
        try {
        	desafio.setOutputFile(output.getBytes());
        } catch (Exception e) {
        	// ignora
        }
        try {
        	desafio.setExplicacaoDesafio(explicao.getBytes());
        } catch (Exception e) {
        	// ignora
        }
        BeanUtilsBean copiadorDePropriedadesNaoNulas = new CopiadorDePropriedadesNaoNulas();
        copiadorDePropriedadesNaoNulas.copyProperties(desafioDaBase, desafio);
		desafioDaBase.merge();
        return "redirect:/desafios/" + encodeUrlPathSegment(desafio.getId().toString(), httpServletRequest);
    }
	
	private static class CopiadorDePropriedadesNaoNulas extends BeanUtilsBean {
		
		@Override
		public void copyProperty(Object bean, String name, Object value)
				throws IllegalAccessException, InvocationTargetException {
			if (value == null) return;
			super.copyProperty(bean, name, value);
		}
		
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST, value = "/carregarSolucao")
	public ModelAndView carregarSolucao(@RequestParam("solucao")MultipartFile file,@RequestParam("id") Long id) throws IOException {
		HttpClient client = HttpClientBuilder.create().build();
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().addBinaryBody("desafio", file.getInputStream(), ContentType.APPLICATION_OCTET_STREAM,"desafio.zip");
		Desafio desafio = Desafio.findDesafio(id);
		multipartEntityBuilder.addTextBody("nValidacoes", desafio.getQuantidadeDeValidacoes().toString());
		HttpPost httpPost = new HttpPost("http://dry-spire-8208.herokuapp.com/");
		//HttpPost httpPost = new HttpPost("http://localhost:8080/");
		httpPost.setEntity(multipartEntityBuilder.build());
		HttpResponse httpResponse = client.execute(httpPost);
		byte[] byteArray = IOUtils.toByteArray(httpResponse.getEntity().getContent());		
		String jsonResultado = new String(byteArray);
		Resultado resultadoResposta = gson.fromJson(jsonResultado, Resultado.class);
		ModelAndView modelAndView = new ModelAndView("forward:/desafios/" + id);
		if (resultadoResposta.isExecutadoCorretamente()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    String name = auth.getName();
			UsuarioDesafio usuarioDesafio = new UsuarioDesafio();
			usuarioDesafio.setEmail(name);
			usuarioDesafio.setIdDesafio(id);
			UsuarioDesafio usuarioDesafioDaBase = usuarioDesafioRepository.findOne(usuarioDesafio.getUsuarioDesafioPK());
			if (usuarioDesafioDaBase != null) {
				usuarioDesafio = usuarioDesafioDaBase;
				usuarioDesafio.setCompletado(true);
			}
			usuarioDesafio.setSolucao(file.getBytes());
			usuarioDesafio.setCbo(resultadoResposta.getCboMedia());
			usuarioDesafio.setDit(resultadoResposta.getDitMedia());
			usuarioDesafio.setLcom1(resultadoResposta.getLcomMedia());
			usuarioDesafio.setNoc(resultadoResposta.getNocMedia());
			usuarioDesafio.setRfc(resultadoResposta.getRfcMedia());
			usuarioDesafio.setNpm(resultadoResposta.getNpmMedia());
			usuarioDesafio.setWmc(resultadoResposta.getWmcMedia());
			usuarioDesafio.setDatasSubmissao(new Date());
			usuarioDesafio.setTempoTotalCompilacao(resultadoResposta.getTempoDeCompilacao());
			usuarioDesafio.setTempoTotalExecucao(resultadoResposta.getTempoDeExecucao());
			usuarioDesafio.setQuatidadeClasses(resultadoResposta.getQuantidadeClasses());
			definirResultadoDasMedias(id, resultadoResposta);
			if (usuarioDesafio.isCompletado()) {
				usuarioDesafioRepository.save(usuarioDesafio);
				modelAndView.addObject("jaFoiCompletado", true);
			} else {
				usuarioDesafioRepository.save(usuarioDesafio);
				userInfuserProvider.addPoints(desafio.getDificuldade().getPontuacao(), name);
				modelAndView.addObject("pontuacao",desafio.getDificuldade().getPontuacao());
			}
			long numeroDesafiosComplentadosPeloUsuario = usuarioDesafioRepository.countByEmail(name);
			if (numeroDesafiosComplentadosPeloUsuario == 1) {
				userInfuserProvider.addBadge("teste-Estacionamento_Facil-private", name);
			}
			modelAndView.addObject("executadoCorretamente", true);
		} else {
			modelAndView.addObject("executadoCorretamente", false);
		}
		modelAndView.addObject("resultado", gson.toJson(resultadoResposta));
		return modelAndView;
	}


	private void definirResultadoDasMedias(Long id, Resultado resultadoResposta) {
		List<DesafioMetricas> metricasDesafio = desafioMetricasRepositorio.findByIdDesafio(id);
		for (DesafioMetricas desafioMetricas : metricasDesafio) {
			String metrica = desafioMetricas.getMetrica();
			if (desafioMetricas.getValorMetrica() == null) {
				desafioMetricas.setValorMetrica(0D);
			}
			if ("wmc".equalsIgnoreCase(metrica) && resultadoResposta.getWmcMedia() > desafioMetricas.getValorMetrica()) {
				resultadoResposta.setWmcBad("Classes do seu programa possuem uma complexidade muito alta, verifique a quantidade de metodos de suas classes e tente pensar melhor nas responsabilidades.");
			}
			if ("cbo".equalsIgnoreCase(metrica) && resultadoResposta.getCboMedia() > desafioMetricas.getValorMetrica()) {
				resultadoResposta.setCboBad("Classes do seu programa possuem um grande numero de relacionamentos, o que pode indicar um alto acoplamento entre as classes. Tente diminuir os mesmos");
			}
			if ("dit".equalsIgnoreCase(metrica) && resultadoResposta.getDitMedia() > desafioMetricas.getValorMetrica()) {
				resultadoResposta.setDitBad("As classes do seu projeto herdam um grande numero de classes. Tente mudar de estrategia, tente compor as classes ao inves de estende-las");
			}
			if ("lcom1".equalsIgnoreCase(metrica) && resultadoResposta.getLcomMedia() > desafioMetricas.getValorMetrica()) {
				resultadoResposta.setLcomBad("Sua classes possuem propriedades que nao sao usadas por todos os metodos, tenta extrair esses metodos para outras classes");
			}
			if ("noc".equalsIgnoreCase(metrica) && resultadoResposta.getNocMedia() > desafioMetricas.getValorMetrica()) {
				resultadoResposta.setNocBad("O numero de classes filhas do seu projeto esta alto, tente nao utilizar tanta heranca, troque para composicao");
			}
			if ("rfc".equalsIgnoreCase(metrica) && resultadoResposta.getRfcMedia() > desafioMetricas.getValorMetrica()) {
				resultadoResposta.setRfcBad("Para conseguir uma resposta no seu programa e necessario passar por muitas classes, tente melhorar as responsabilidades de suas classes");
			}
			if ("npm".equalsIgnoreCase(metrica) && resultadoResposta.getNpmMedia() > desafioMetricas.getValorMetrica()) {
				resultadoResposta.setNpmBad("O numero de metodos publicos na sua classe e muito alto ");
			}
		}
	}
	
	@RequestMapping("/dadosSolucao")
	@ResponseBody
	public void dadosSolucao(@RequestParam("id") Long desafioId,@RequestParam String email, HttpServletResponse response) throws IOException {
		UsuarioDesafio usuarioDesafio = usuarioDesafioRepository.findByEmailAndIdDesafio(email, desafioId).get(0);
		Resultado resultado = new Resultado();
		resultado.setCboMedia(usuarioDesafio.getCbo());
		resultado.setDitMedia(usuarioDesafio.getDit());
		resultado.setLcomMedia(usuarioDesafio.getLcom1());
		resultado.setNocMedia(usuarioDesafio.getNoc());
		resultado.setNpmMedia(usuarioDesafio.getNpm());
		resultado.setRfcMedia(usuarioDesafio.getRfc());
		resultado.setWmcMedia(usuarioDesafio.getWmc());
		resultado.setTempoDeCompilacao(usuarioDesafio.getTempoTotalCompilacao());
		resultado.setTempoDeExecucao(usuarioDesafio.getTempoTotalExecucao());
		definirResultadoDasMedias(desafioId, resultado);
		IOUtils.write(gson.toJson(resultado), response.getOutputStream());
	}
	
	@RequestMapping(value = "/downloadDesafio/{id}")
	@ResponseBody
	public void downloadDesafio(@PathVariable("id") Long id, HttpServletResponse response,HttpServletRequest request, Model uiModel) throws IOException, URISyntaxException {
		Desafio desafio = Desafio.findDesafio(id);
		File file = new File("desafio-"+id +".zip");
		synchronized (file) {
//			if (!file.exists()) {
				URL resource = getClass().getClassLoader().getResource("padrao.zip");
				File projetoPadrao = new File(resource.toURI());
				ZipInputStream zin = new ZipInputStream(new FileInputStream(projetoPadrao));
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
				ZipEntry nextEntry = zin.getNextEntry();
				byte[] buf = new byte[1024];
				while(nextEntry != null) {
					out.putNextEntry(nextEntry);
					int len;
					while ((len = zin.read(buf)) > 0) {
		                out.write(buf, 0, len);
					}
					out.closeEntry();
					nextEntry = zin.getNextEntry();
				}
				addToZip(desafio.getInputFile(), out, "input.txt");
				addToZip(desafio.getOutputFile(), out, "output.txt");
				addToZip(desafio.getExplicacaoDesafio(), out, "explicacao.pdf");
				IOUtils.closeQuietly(zin);
				IOUtils.closeQuietly(out);
	//		}
			FileInputStream input = new FileInputStream(file);
			byte[] byteArray = IOUtils.toByteArray(input);
			IOUtils.closeQuietly(input);
			IOUtils.write(byteArray, response.getOutputStream());
			response.setContentType("application/zip");
			response.addHeader("Content-Disposition", "attachment; filename=\"projeto.zip\"");
			response.setHeader("Content-Length", String.valueOf(byteArray.length));
		}
	}

	private void addToZip(byte[] inputFile, ZipOutputStream out, String name)
			throws IOException {
		ZipEntry eNovo = new ZipEntry(name);
		out.putNextEntry(eNovo);
		out.write(inputFile, 0, inputFile.length);
		out.closeEntry();
	}
	 
	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value="nomeDesafio",required=false) String nomeDesafio, @RequestParam(value="dificuldade", required=false)String dificuldade,@RequestParam(value ="find",required=false) String find,@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (StringUtils.isEmpty(nomeDesafio) && StringUtils.isEmpty(nomeDesafio)) {
        	return buscaPadrao(page, size, uiModel);
        }
        return filtrarPorFinder(uiModel, nomeDesafio, dificuldade);
    }

	private String buscaPadrao(Integer page, Integer size, Model uiModel) {
		if (page != null || size != null) {
            filtrarPorFinder(page, size, uiModel);
        } else {
            uiModel.addAttribute("desafios", Desafio.findAllDesafios());
        }
        return "desafios/list";
	}

	private void filtrarPorFinder(Integer page, Integer size, Model uiModel) {
		int sizeNo = size == null ? 10 : size.intValue();
		final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
		uiModel.addAttribute("desafios", Desafio.findDesafioEntries(firstResult, sizeNo));
		float nrOfPages = (float) Desafio.countDesafios() / sizeNo;
		uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
	}
	
	private String filtrarPorFinder(Model uiModel, String nomeDesafio,String dificuldade) {
		TypedQuery<Desafio> desafios = null;
		if (StringUtils.isNotEmpty(nomeDesafio)) {
			desafios = Desafio.findDesafiosByNomeDesafioLike(nomeDesafio);
		}  else if (StringUtils.isNotEmpty(dificuldade)) {
			Dificuldade dificuldadeEnum = Dificuldade.valueOf(dificuldade);
			desafios = Desafio.findDesafiosByDificuldade(dificuldadeEnum);
		} 
		uiModel.addAttribute("desafios", desafios.getResultList());
		return "desafios/list";
	}
	
    @RequestMapping(value = "/{id}", produces = "text/html")
    public ModelAndView show(@PathVariable("id") Long id, Model uiModel, HttpServletRequest request) {
    	Object attribute = request.getAttribute("resultado");
    	ModelAndView modelAndView = new ModelAndView("desafios/show");
    	Desafio findDesafio = Desafio.findDesafio(id);
    	List<UsuarioDesafio> usuariosDoDesafio = new ArrayList(findDesafio.getUsuariosDesafios());
    	final List<DesafioMetricas> metricasDesafio = findDesafio.getDesafioMetricas();
    	ordernarUsuariosDesteDesafioPorMetricas(usuariosDoDesafio, metricasDesafio);
		modelAndView.addObject("desafio", findDesafio);
        modelAndView.addObject("itemId", id);
        if (attribute != null) {
        	modelAndView.addObject("resultado",attribute);
        }
        modelAndView.addObject("usuariosDoDesafio", usuariosDoDesafio);
        return modelAndView;
    }

	private void ordernarUsuariosDesteDesafioPorMetricas(
			List<UsuarioDesafio> usuariosDoDesafio,
			final List<DesafioMetricas> metricasDesafio) {
		Collections.sort(usuariosDoDesafio, new Comparator<UsuarioDesafio>() {
    		@Override
			public int compare(UsuarioDesafio o1, UsuarioDesafio o2) {
				for (DesafioMetricas desafioMetricas : metricasDesafio) {
					if ("WMC".equalsIgnoreCase(desafioMetricas.getMetrica())) {
						int wmc = o1.getWmc().compareTo(o2.getWmc());
						if (wmc == 0) {
							continue;
						}
						return wmc;
					}  
					if ("DIT".equalsIgnoreCase(desafioMetricas.getMetrica())) {
						int dit = o1.getDit().compareTo(o2.getDit());
						if (dit == 0) {
							continue;
						}
						return dit;
					}
					if ("RFC".equalsIgnoreCase(desafioMetricas.getMetrica())) {
						int rfc = o1.getRfc().compareTo(o2.getRfc());
						if (rfc == 0) {
							continue;
						}
						return rfc;
					}
					if ("NOC".equalsIgnoreCase(desafioMetricas.getMetrica())) {
						int noc = o1.getNoc().compareTo(o2.getNoc());
						if (noc == 0) {
							continue;
						}
						return noc;
					}
					if ("NPM".equalsIgnoreCase(desafioMetricas.getMetrica())) {
						int npm = o1.getNpm().compareTo(o2.getNpm());
						if (npm == 0) {
							continue;
						}
						return npm;
					}
					if ("LCOM1".equalsIgnoreCase(desafioMetricas.getMetrica())) {
						int lcom1 = o1.getLcom1().compareTo(o2.getLcom1());
						if (lcom1 == 0) {
							continue;
						}
						return lcom1;
					}
					if ("CBO".equalsIgnoreCase(desafioMetricas.getMetrica())) {
						int cbo = o1.getCbo().compareTo(o2.getCbo());
						if (cbo == 0) {
							continue;
						}
						return cbo;
					}
				}
				throw new IllegalArgumentException("Metrica invalida");
			}
		});
	}
	
	@RequestMapping("/carregarSolucaoUsuario")
	@ResponseBody
    public void carregarSolucaoUsuario(@RequestParam String email,@RequestParam("id") Long idDesafio, HttpServletResponse response) throws IOException {
    	UsuarioDesafioPK usuarioDesafioPkFiltro = new UsuarioDesafioPK();
    	usuarioDesafioPkFiltro.setEmail(email);
    	usuarioDesafioPkFiltro.setIdDesafio(idDesafio);
    	UsuarioDesafio usuarioDesafio = usuarioDesafioRepository.findOne(usuarioDesafioPkFiltro);
    	byte[] solucao = usuarioDesafio.getSolucao();
    	IOUtils.write(solucao, response.getOutputStream());
    	response.setContentType("application/zip");
		response.addHeader("Content-Disposition", "attachment; filename=\"solucao.zip\"");
		response.setHeader("Content-Length", String.valueOf(solucao.length));
    }

	
}
