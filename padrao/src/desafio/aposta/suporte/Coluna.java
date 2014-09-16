package desafio.aposta.suporte;

public enum Coluna {

	COLUNA_1(0),
	COLUNA_2(12),
	COLUNA_3(24);
	
	private Integer somatorioColuna;
	
	private Coluna(Integer somatorioColuna) {
		this.somatorioColuna = somatorioColuna;	
	}
	
	public Integer getSomatorioColuna() {
		return somatorioColuna;
	}
	
}
