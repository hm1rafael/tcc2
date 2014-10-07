package tcc2.portal.repositorio;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import tcc2.portal.domain.UsuarioDesafio;

@RooJpaRepository(domainType = UsuarioDesafio.class)
public interface UsuarioDesafioRepository {
	
	@Query("select count(u) from UsuarioDesafio u where u.usuarioDesafioPK.email = ?1")
	public long countByEmail(String email);

	@Query("select u from UsuarioDesafio u where u.usuarioDesafioPK.email = ?1 and u.usuarioDesafioPK.idDesafio = ?2")
	List<UsuarioDesafio> findByEmailAndIdDesafio(String email, Long idDesafio);
	
}
