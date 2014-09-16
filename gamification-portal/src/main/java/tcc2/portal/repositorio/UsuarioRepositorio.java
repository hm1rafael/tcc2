package tcc2.portal.repositorio;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import tcc2.portal.domain.Usuario;

@RooJpaRepository(domainType = Usuario.class)
public interface UsuarioRepositorio {
	
	Usuario findByEmailAndPass(String email, String pass);

    Usuario findByEmail(String email);
    
}
