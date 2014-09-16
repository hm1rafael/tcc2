package br.univali.tcc2;

import java.util.ArrayList;
import java.util.List;

public class CodigoFonte {

	private final List<ArquivoCodigoFonte> arquivosDeCodigo = new ArrayList<ArquivoCodigoFonte>();
	private Erros erros;
	private String entrada;
	private String saida;

	public void adicionarArquivoDeCodigo(byte[] arquivoDeCodigo,
			String nomeArquivo) {
		this.arquivosDeCodigo.add(new ArquivoCodigoFonte(nomeArquivo,
				arquivoDeCodigo));
	}

	public List<ArquivoCodigoFonte> getArquivosDeCodigo() {
		return this.arquivosDeCodigo;
	}

	public final Erros getErros() {
		return this.erros;
	}
	
	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}
	
	public void setSaida(String saida) {
		this.saida = saida;
	}
	
	public String getEntrada() {
		return entrada;
	}
	
	public String getSaida() {
		return saida;
	}

}
