package com.etoile.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/freeboard")
public class Freeboard {
	@GetMapping("")
	public String doFreeboard() {
		return "redirect:/freeboard/list";
	}
	@GetMapping("/list")
	public String doList(HttpServletRequest req, Model model) {
		try {
			System.out.println("/list");
			return "freeboard/list";
		} catch(Exception e) {

		}
		return "freeboard/list";
	}
	@GetMapping("/create")
	public String doCreate(HttpServletRequest req, Model model) {
		try {
			return "freeboard/create";
		} catch (Exception e) {
		}
		return "freeboard/create";
	}
}
