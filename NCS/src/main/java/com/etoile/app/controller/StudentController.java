package com.etoile.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired _Student std;
	
	@PostMapping("/list")
	public String doList(HttpServletRequest req) {
		
	}
	@PostMapping("/add")
	public String doAdd(HttpServletRequest req) {
		
	}
	@PostMapping("/get")
	public String doGet(HttpServletRequest req) {
		
	}
	@PostMapping("/delete")
	public String doDelete(HttpServletRequest req) {
		
	}
	@PostMapping("/update")
	public String doUpdate(HttpServletRequest req) {
		
	}
}
