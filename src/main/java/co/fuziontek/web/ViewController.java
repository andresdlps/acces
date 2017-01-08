package co.fuziontek.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author FT
 */
@Controller
public class ViewController {

	@RequestMapping("/")
	public String root() {
		return "redirect:acces/index";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping("/acces/index")
	public String userIndex() {
		return "acces/index";
	}
        
        @RequestMapping("/acces/datos")
	public String datos() {
		return "acces/datos";
	}
        
        @RequestMapping("/acces/calcular")
	public String calcular() {
		return "acces/calcular";
	}
        
        @RequestMapping("/acces/ayuda")
	public String ayuda() {
		return "acces/ayuda";
	}
        
        @RequestMapping("/acces/insumosash")
	public String insumosash() {
		return "acces/insumosash";
	}
        
        @RequestMapping("/acces/insumoscai")
	public String insumoscai() {
		return "acces/insumoscai";
	}
        
        @RequestMapping("/acces/insumoscau")
	public String insumoscau() {
		return "acces/insumoscau";
	}
        
        @RequestMapping("/acces/consolidadoash")
	public String consolidadoash() {
		return "acces/consolidadoash";
	}
        
        @RequestMapping("/acces/consolidadocai")
	public String consolidadocai() {
		return "acces/consolidadocai";
	}
        
        @RequestMapping("/acces/consolidadocau")
	public String consolidadocau() {
		return "acces/consolidadocau";
	}
        
        @RequestMapping("/acces/consolidadogeneral")
	public String consolidadogeneral() {
		return "acces/consolidadogeneral";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

}
