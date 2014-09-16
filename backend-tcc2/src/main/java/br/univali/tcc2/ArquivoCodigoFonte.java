package br.univali.tcc2;

public class ArquivoCodigoFonte {

	private final String nome;
	private final byte[] arquivoCodigoFonte;

	public ArquivoCodigoFonte(String nome, byte[] arquivoCodigoFonte) {
		this.nome = nome;
		this.arquivoCodigoFonte = arquivoCodigoFonte;
	}

	public String getNome() {
		return this.nome;
	}

	public byte[] getArquivoCodigoFonte() {
		return this.arquivoCodigoFonte;
	}

}
