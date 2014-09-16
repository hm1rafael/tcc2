package desafio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class Utils {

	private static Scanner scannerEntrada;
	private static Scanner scannerSaida;
	private static Integer validacoes = 0;
	
	static {
		try{
			scannerEntrada = new Scanner(new File("tmp/input.txt"));
			scannerSaida = new Scanner(new File("tmp/output.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}	
	
	public static String getProximoCaracter() {
		return scannerEntrada.next();
	}
	
	public static Integer getProximoCaracterInteiro() {
		return Integer.valueOf(getProximoCaracter());
	}
	
	public static void validarSaida(Object object) {
		validacoes++;
		String valor = object.toString();
		if (!valor.equals(scannerSaida.next())) {
			throw new IllegalStateException("Valor resultante incorreto.");
		}
	}
	
	
	
}
