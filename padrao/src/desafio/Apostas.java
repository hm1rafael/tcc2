package desafio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import desafio.aposta.Aposta;

public class Apostas {

	private Map<Integer, List<Aposta>> todasApostas = new HashMap<Integer, List<Aposta>>();
	
	private Aposta apostaGanhadora;
	
	public Integer quemGanhou(Integer numeroRoleta) {
		Set<Entry<Integer, List<Aposta>>> apostasDosJogadores = this.todasApostas.entrySet();
		for (Entry<Integer, List<Aposta>> apostaDeUmJogador : apostasDosJogadores) {
			boolean algumaApostaVencedora = verificarApostasDoJogador(numeroRoleta, apostaDeUmJogador);
			if (algumaApostaVencedora) {
				return apostaDeUmJogador.getKey();
			}
		}
		return null;
	}

	private boolean verificarApostasDoJogador(Integer numeroRoleta,
			Entry<Integer, List<Aposta>> apostaDeUmJogador) {
		for (Aposta aposta : apostaDeUmJogador.getValue()) {
			if (aposta.isGanhadora(numeroRoleta)) { 
				apostaGanhadora = aposta;
				return true;
			}
		}
		return false;
	}
	
	public void adicionarAposta(Integer jogadorId, Aposta aposta) {
		List<Aposta> apostasDoJogador = this.todasApostas.get(jogadorId);
		if (apostasDoJogador == null) {
			apostasDoJogador = new ArrayList<Aposta>();
		}
		apostasDoJogador.add(aposta);
		this.todasApostas.put(jogadorId, apostasDoJogador);
	}

	public Aposta getApostaGanhadora() {
		return apostaGanhadora;
	}
	
}
