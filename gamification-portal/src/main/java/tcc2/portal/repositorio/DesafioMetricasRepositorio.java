package tcc2.portal.repositorio;
import java.util.List;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import tcc2.portal.domain.DesafioMetricas;

@RooJpaRepository(domainType = DesafioMetricas.class)
public interface DesafioMetricasRepositorio {
	
		List<DesafioMetricas> findByIdDesafio(long idDesafio);
	
}
