package desafio.aposta;

import desafio.aposta.suporte.Coluna;


public class ApostaFactory {

	public static Aposta getAposta(String valorAposta, String apostaId) {
		Long aposta = Long.valueOf(valorAposta);
		try {
			Integer numeroDireto = Integer.valueOf(apostaId);
			return new ApostaNumeroDireto(numeroDireto, aposta);
		} catch (Exception e) {
			//Não é uma aposta direta
		}
		Coluna coluna = Coluna.valueOf(apostaId);
		return new ApostaColuna(coluna, aposta);
	}

	
}
