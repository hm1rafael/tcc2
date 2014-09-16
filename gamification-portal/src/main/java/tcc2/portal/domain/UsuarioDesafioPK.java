package tcc2.portal.domain;

import java.io.Serializable;

public class UsuarioDesafioPK implements Serializable{

	private String email;
	
	private Long idDesafio;
	
	public String getEmail() {
		return email;
	}
	
	public Long getIdDesafio() {
		return idDesafio;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setIdDesafio(Long idDesafio) {
		this.idDesafio = idDesafio;
	}
	
}
