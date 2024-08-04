package com.etoile.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	@GetMapping("/")
	public String home(HttpServletRequest req, HttpSession s, Model model) {
		model.addAttribute("title","하이미디어 일산점");
		model.addAttribute("msg","");
		String mobile = (String)s.getAttribute("mobile");
		if(mobile==null || mobile.equals("")) {
			return "login";
		} else {
			return "home";
		}
	}
	@GetMapping("/logout")
	public String doLogout(HttpSession s, Model model) {
		s.invalidate();
		return "redirect:/";
	}
	
}
