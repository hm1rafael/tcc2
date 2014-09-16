package desafio.aposta;

import desafio.wrapper.Numeros;

public abstract class Aposta {

	protected Long valorAposta;
	protected Numeros numerosGanhadores = new Numeros();
	
	public Aposta(Long valorAposta) {
		this.valorAposta = valorAposta;
	}
	
	public Numeros getNumerosGanhadores() {
		return numerosGanhadores;
	}
	
	public boolean isGanhadora(Integer numeroRoleta) {
		return numerosGanhadores.contido(numeroRoleta);
	}
	
	
	public Long getValorAposta() {
		return valorAposta;
	}
	
	public void setValorAposta(Long valorAposta) {
		this.valorAposta = valorAposta;
	}
	
	public Long calcularPremiacao() {
		return this.valorAposta * getValorMultiplicador();
	}

	protected abstract Integer getValorMultiplicador();
	
}
