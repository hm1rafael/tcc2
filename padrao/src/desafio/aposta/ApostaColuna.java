package desafio.aposta;

import desafio.aposta.suporte.Coluna;

public class ApostaColuna extends Aposta {

	private static final int TAMANHO_COLUNA = 12;
	
	public ApostaColuna(Coluna coluna, Long valorAposta) {
		super(valorAposta);
		Integer somatorioColuna = coluna.getSomatorioColuna();
		for (int i = 0; i < TAMANHO_COLUNA;i++) {
			this.numerosGanhadores.adicionarNumeros(i + somatorioColuna);
		}
	}

	@Override
	protected Integer getValorMultiplicador() {
		return 2;
	}

}

