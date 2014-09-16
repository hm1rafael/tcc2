package tcc2.portal.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tcc2.portal.provider.UserInfuserProvider;

@RequestMapping("/trofeu/**")
@Controller
public class TrofeuController {

	@Autowired
	private UserInfuserProvider userInfuserProvider;
	
    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }

    @RequestMapping
    public ModelAndView index() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String name = authentication.getName();
		String throphyCase = userInfuserProvider.getThrophyCase(name);
    	ModelAndView modelAndView = new ModelAndView("trofeu/index");
    	modelAndView.addObject("trofeuCase", throphyCase);
        return modelAndView;
    }
}
