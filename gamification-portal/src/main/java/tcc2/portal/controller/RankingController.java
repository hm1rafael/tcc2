package tcc2.portal.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.userinfuser.client.UserInfuser;

import tcc2.portal.provider.UserInfuserProvider;
import tcc2.portal.provider.details.UsuarioUserInfUser;

@RequestMapping("/ranking/**")
@Controller
public class RankingController {

	@Autowired
	private UserInfuserProvider userInfuserProvider;
	
    @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("lallala");
    }

    @RequestMapping
    public String index() {
        return "ranking/index";
    }
    
    @RequestMapping(value = "/carregarRanking")
    @ResponseBody
    public String carregarRanking() {
    	return userInfuserProvider.getLeaderBoard();
    }
    
}
