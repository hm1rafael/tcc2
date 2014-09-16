package br.univali.tcc2;

import java.io.File;

public class Usuario {

	/**
	 * Nome que usuario pra se cadastrar no sistema
	 */
	private String indentificadorDoUsuario;

	private File diretorioDesafios;

	public File getDiretorioDesafios() {
		return this.diretorioDesafios;
	}

	public String getIndentificadorDoUsuario() {
		return this.indentificadorDoUsuario;
	}

	public void setDiretorioDesafios(File diretorioDesafios) {
		this.diretorioDesafios = diretorioDesafios;
	}

	public void setIndentificadorDoUsuario(String indentificadorDoUsuario) {
		this.indentificadorDoUsuario = indentificadorDoUsuario;
	}

}
