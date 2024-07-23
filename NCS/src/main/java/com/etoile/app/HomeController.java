package com.etoile.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home() {
		return "home";
	}
	@GetMapping("/login") 
	public String login(HttpServletRequest req, Model m) {
		m.addAttribute("title","하이미디어 일산")
		return "login"; 
	}
	@PostMapping("/checkuser")
	public String checkUser(HttpServletRequest req) {
		
	}
}
