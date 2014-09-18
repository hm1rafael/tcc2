package br.univali.tcc2.controller;

import gr.spinellis.ckjm.ClassMetrics;

import java.io.IOException;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.apache.bcel.classfile.JavaClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tcc2.desafio.MediaMetricas;
import br.univali.tcc2.Classe;
import br.univali.tcc2.CodigoFonte;
import br.univali.tcc2.DesafioUsuario;
import br.univali.tcc2.compiler.VerificadorCodigo;
import br.univali.tcc2.domain.Resultado;
import br.univali.tcc2.exception.ErroCompilacaoException;
import br.univali.tcc2.executor.ExecutorCodigo;
import br.univali.tcc2.metrics.AvaliadorMetricas;

@RestController
public class DesafioController {

	@Autowired
	private VerificadorCodigo verificadorCodigo;

	@Autowired
	private AvaliadorMetricas avaliadorMetricas;

	@Autowired
	private ExecutorCodigo executorCodigo;
	
	@Autowired
	private ExtratorCodigoFonte extratorCodigoFonte;
	
	@RequestMapping(value = "/")
	public @ResponseBody
	Resultado avaliarDesafio(@RequestParam("desafio") MultipartFile zipDesafio, @RequestParam("nValidacoes") int numeroDeValidacoesQueDevemSerRealizadas)
			throws IOException {		
		ZipInputStream zin = new ZipInputStream(zipDesafio.getInputStream());
		CodigoFonte codigoFonte = this.extratorCodigoFonte.carregarCodigoFonte(zin);
		DesafioUsuario desafioUsuario = new DesafioUsuario();
		desafioUsuario.setCodigoFonte(codigoFonte);
		try {
			List<Classe> classes = this.verificadorCodigo.compilarCodigo(desafioUsuario);
			boolean codigoExecutadoCorretamente= executorCodigo.executarCodigoUsuario(classes, numeroDeValidacoesQueDevemSerRealizadas, codigoFonte);
			MediaMetricas mediaMetricas = new MediaMetricas(classes.size());
			for (Classe classe : classes) {
				if (classe.getJavaClass() != null) {
					ClassMetrics metricas = this.avaliadorMetricas
							.avaliarMetricasDaClasse(classe.getJavaClass());
					mediaMetricas.addMetris(metricas);
				}
			}
			return new Resultado(mediaMetricas, codigoExecutadoCorretamente);
		} catch (ErroCompilacaoException e) {
			return new Resultado();
		}
	}
	
	@RequestMapping(value = "/teste")
	public String teste() {
		return "OK";
	}
 
}
