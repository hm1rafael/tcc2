package tcc2.portal.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class DesafioMetricas {

	public DesafioMetricas() {
		// TODO Auto-generated constructor stub
	}
	
	public DesafioMetricas(String metrica) {
		this.metrica = metrica;
	}
	
    /**
     */
    private String metrica;

    private Long idDesafio;
    
    private Double valorMetrica; 
    
}
