package br.univali.tcc2;

public class Erro {

	private PosicaoErro posicaoErro;
	private String nomeArquivo;

	public PosicaoErro getPosicaoErro() {
		return this.posicaoErro;
	}

	public void setPosicaoErro(PosicaoErro posicaoErro) {
		this.posicaoErro = posicaoErro;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getNomeArquivo() {
		return this.nomeArquivo;
	}

}
