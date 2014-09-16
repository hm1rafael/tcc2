package tcc2.portal.domain;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Transient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findDesafiosByNomeDesafioLike", "findDesafiosByDificuldade" })
public class Desafio {

    /**
     */
    private String nomeDesafio;

    /** 
     */
    @Enumerated
    private Dificuldade dificuldade;

    @Basic(fetch = FetchType.LAZY)
    private byte[] inputFile;
    
    @Basic(fetch = FetchType.LAZY)
    private byte[] outputFile;
    
    @Basic(fetch = FetchType.LAZY)
    private byte[] explicacaoDesafio;
    
    private Integer quantidadeDeValidacoes;

    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER, mappedBy= "idDesafio")
    private List<DesafioMetricas> desafioMetricas = new ArrayList<DesafioMetricas>();
    
    @OneToMany(fetch=FetchType.EAGER,mappedBy = "usuarioDesafioPK.idDesafio")
    private Set<UsuarioDesafio> usuariosDesafios = new HashSet<UsuarioDesafio>();
    
    @Transient
    public List<UsuarioDesafio> getUsuarioDesafiosList() {
    	if (CollectionUtils.isEmpty(usuariosDesafios)) {
    		return new ArrayList<UsuarioDesafio>();
    	}
    	return new ArrayList<UsuarioDesafio>(usuariosDesafios);
    }
    
    
    
}
