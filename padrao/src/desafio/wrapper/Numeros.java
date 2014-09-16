package desafio.wrapper;

import java.util.HashSet;
import java.util.Set;

public class Numeros {

	Set<Integer> numeros = new HashSet<Integer>();
	
	public void adicionarNumeros(Integer... numero) {
		for (Integer integer : numero) {
			numeros.add(integer);
		}
	}
	
	public boolean contido(Integer numero) {
		return numero != null && numeros.contains(numero);
	}
	
}
