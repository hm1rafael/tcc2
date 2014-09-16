package desafio;

import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;

import desafio.aposta.Aposta;
import desafio.aposta.ApostaFactory;

public class Execucao {

	
	public void processarEntrada() {
		List<Jogador> jogadores = new ArrayList<Jogador>();
		Integer contadorDeJogadores = Utils.getProximoCaracterInteiro();
		Mesa mesa = new Mesa();
		for (int i = 1;i <= contadorDeJogadores; i++) {
			jogadores.add(new Jogador(i));
			mesa.apostar(i, ApostaFactory.getAposta(Utils.getProximoCaracter(), Utils.getProximoCaracter()));
		}
		Integer idGanhador = mesa.getIdGanhador(Utils.getProximoCaracterInteiro());
		Aposta apostaGanhadora = mesa.getApostaGanhadora();
		Utils.validarSaida(idGanhador);
		Utils.validarSaida(apostaGanhadora.calcularPremiacao());
	}
	
}
