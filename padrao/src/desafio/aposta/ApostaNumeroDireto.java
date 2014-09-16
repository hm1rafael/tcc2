package desafio.aposta;


public class ApostaNumeroDireto extends Aposta {
	
	public ApostaNumeroDireto(Integer numero, Long valorAposta) {
		super(valorAposta);
		this.numerosGanhadores.adicionarNumeros(numero);
		this.valorAposta = valorAposta;
	}

	@Override
	protected Integer getValorMultiplicador() {
		return 35;
	}

}
