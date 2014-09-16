package br.univali.tcc2;

import java.util.ArrayList;
import java.util.List;

public class Erros {

	private final List<Erro> errosDeCompilacao = new ArrayList<Erro>();

	public void adicionarErro(Erro erro) {
		this.errosDeCompilacao.add(erro);
	}

	public void adicionarErros(Erros erros) {
		List<Erro> errosDeCompilacao = erros.errosDeCompilacao;
		for (Erro erro : errosDeCompilacao) {
			errosDeCompilacao.add(erro);
		}
	}

	public List<Erro> getErrosDeCompilacao() {
		return this.errosDeCompilacao;
	}

}
