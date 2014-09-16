package tcc2.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tcc2.portal.domain.Usuario;
import tcc2.portal.service.CadastroService;

@RequestMapping("/cadastro/**")
@Controller
public class CadastroController {

	@Autowired
	private CadastroService cadastroService;
	
	@RequestMapping
	public String index() {
		return "cadastro/index";
	}

	@RequestMapping(value = "/save")
	public String save(@RequestParam String email, @RequestParam String password, @RequestParam String password_r) {
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setPass(password);
		this.cadastroService.cadastrarUsuario(usuario, password_r);
		return "login";
	}

}
