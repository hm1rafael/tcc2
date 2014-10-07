package tcc2.portal.domain;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class UsuarioDesafio {

    private byte[] solucao;
    
    @Transient
    private boolean completado;
    
    @EmbeddedId
    private UsuarioDesafioPK usuarioDesafioPK = new UsuarioDesafioPK();
	
    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date datasSubmissao;
    
    public void setEmail(String email) {
    	this.usuarioDesafioPK.setEmail(email);
    }
    
    public void setIdDesafio(Long idDesafio) {
    	this.usuarioDesafioPK.setIdDesafio(idDesafio);
    }
    
    public String getEmail() {
    	return this.usuarioDesafioPK.getEmail();
    }
    
    public Long getId() {
    	return 0L;
    }
    
    /**
     */
    private Double wmc;

    /**
     */
    private Double dit;

    /**
     */
    private Double cbo;

    /**
     */
    private Double rfc;

    /**
     */
    private Double noc;

    /**
     */
    private Double npm;

    /**
     */
    private Double lcom1;
    
    private Double tempoTotalExecucao;
    
    private Double tempoTotalCompilacao;
    
    private Integer quatidadeClasses;
    
}
