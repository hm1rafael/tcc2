package tcc2.portal.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tcc2.portal.domain.Usuario;
import tcc2.portal.exception.NaoConseguiuGerarContaUserInfUserException;
import tcc2.portal.exception.UsuarioJaExisteException;
import tcc2.portal.provider.UserInfuserProvider;
import tcc2.portal.repositorio.UsuarioRepositorio;

@Service
public class CadastroService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private UserInfuserProvider userInfuserProvider;

	public void cadastrarUsuario(Usuario usuario, String password_r) {
		validarUsuario(usuario, password_r);
		boolean userAdicionado = this.userInfuserProvider
				.addOrUpdateUser(usuario.getEmail());
		if (!userAdicionado) {
			throw new NaoConseguiuGerarContaUserInfUserException();
		}
		this.usuarioRepositorio.saveAndFlush(usuario);
	}

	private void validarUsuario(Usuario usuario, String password_r) {
		if (!StringUtils.equals(usuario.getPass(), password_r)) {
			throw new IllegalArgumentException(
					"O valor do campo senha nao bate com o que foi redigitado.");
		}
		if (StringUtils.isBlank(usuario.getEmail())) {
			throw new IllegalArgumentException(
					"O valor do campo email deve ser preenchido.");
		}
		if (this.usuarioRepositorio.findByEmail(usuario.getEmail()) != null) {
			throw new UsuarioJaExisteException("Usuario ja existe");
		}
	}

}
