package br.univali.tcc2;

public class PosicaoErro {

	private final int linhaInicio,linhaFim,colunaInicio,colunaFim;

	public PosicaoErro(int linhaInicio, int linhaFim, int colunaInicio,int colunaFim) {
		this.linhaInicio = linhaInicio;
		this.linhaFim = linhaFim;
		this.colunaInicio = colunaInicio;
		this.colunaFim = colunaFim;
	}

	public int getColunaFim() {
		return this.colunaFim;
	}

	public int getColunaInicio() {
		return this.colunaInicio;
	}

	public int getLinhaFim() {
		return this.linhaFim;
	}

	public int getLinhaInicio() {
		return this.linhaInicio;
	}

}
