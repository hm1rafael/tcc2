package desafio;

import desafio.aposta.Aposta;

public class Mesa {
	
	private Apostas apostas = new Apostas();
	
	public void apostar(Integer id, Aposta aposta) {
		apostas.adicionarAposta(id, aposta);
	}
	
	public Integer getIdGanhador(Integer numeroRoleta) {
		return apostas.quemGanhou(numeroRoleta);
	}

	public Aposta getApostaGanhadora() {
		return apostas.getApostaGanhadora();
	}

}
