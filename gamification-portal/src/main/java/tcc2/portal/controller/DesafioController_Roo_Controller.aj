// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package tcc2.portal.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import tcc2.portal.controller.DesafioController;
import tcc2.portal.domain.Desafio;
import tcc2.portal.domain.DesafioMetricas;
import tcc2.portal.domain.Dificuldade;

privileged aspect DesafioController_Roo_Controller {
    
    @RequestMapping(params = "form", produces = "text/html")
    public String DesafioController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Desafio());
        return "desafios/create";
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String DesafioController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Desafio.findDesafio(id));
        return "desafios/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String DesafioController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Desafio desafio = Desafio.findDesafio(id);
        desafio.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/desafios";
    }
    
    void DesafioController.populateEditForm(Model uiModel, Desafio desafio) {
        uiModel.addAttribute("desafio", desafio);
        uiModel.addAttribute("desafiometricases", DesafioMetricas.findAllDesafioMetricases());
        uiModel.addAttribute("dificuldades", Arrays.asList(Dificuldade.values()));
        uiModel.addAttribute("usuariodesafios", usuarioDesafioRepository.findAll());
    }
    
    String DesafioController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
