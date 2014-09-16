package tcc2.portal.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import tcc2.portal.domain.Usuario;
import tcc2.portal.provider.UserInfuserProvider;
import tcc2.portal.provider.details.UsuarioUserInfUser;
import tcc2.portal.repositorio.UsuarioRepositorio;

@Component
public class UserInfUserAutheticationProvider extends
		AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private UserInfuserProvider userInfuserProvider;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authenticatio)
			throws AuthenticationException {
		UsuarioUserInfUser user = assertUsuarioRegistradoNoUserinfuser(username);
		popularIdUsuarioDaBase(username, authenticatio, user);
		return user;
	}

	private void popularIdUsuarioDaBase(String username,
			UsernamePasswordAuthenticationToken authenticatio,
			UsuarioUserInfUser user) {
		Usuario usuarioBase = this.usuarioRepositorio.findByEmailAndPass(
				username, authenticatio.getCredentials().toString());
		if (usuarioBase == null) {
			throw new UsernameNotFoundException(String.format(
					"Usuario : %s não encontrado", username));
		}
		user.setId(usuarioBase.getId());
	}

	private UsuarioUserInfUser assertUsuarioRegistradoNoUserinfuser(
			String username) {
		UsuarioUserInfUser user = this.userInfuserProvider.getUser(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format(
					"Usuario : %s não encontrado", username));
		}
		return user;
	}

}
