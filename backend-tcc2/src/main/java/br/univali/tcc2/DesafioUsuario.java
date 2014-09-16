package br.univali.tcc2;

import java.io.File;
import java.util.List;

public class DesafioUsuario {

	private Usuario usuario;
	private CodigoFonte codigoFonte;

	public CodigoFonte getCodigoFonte() {
		return this.codigoFonte;
	}

	public void setCodigoFonte(CodigoFonte codigoFonte) {
		this.codigoFonte = codigoFonte;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public File getDiretorioDeDesafiosDoUsuario() {
		return this.usuario.getDiretorioDesafios();
	}

	public List<ArquivoCodigoFonte> getCodigo() {
		return this.codigoFonte.getArquivosDeCodigo();
	}

	public void setErros(Erros erros) {
		this.codigoFonte.getErros().adicionarErros(erros);
	}

}
