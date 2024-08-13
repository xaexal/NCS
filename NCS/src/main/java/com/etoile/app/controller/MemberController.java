package com.etoile.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {
	@Autowired _Member _mem;
	
	@PostMapping("/insert")
	public String doInsert(HttpServletRequest req,Model model) {
		int result=0;
		try {
			String mobile = req.getParameter("mobile");
			String passcode = req.getParameter("passcode");
			result = _mem.insert(mobile, passcode);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}
	@PostMapping("/update")
	public String doUpdate(HttpServletRequest req, HttpSession s, Model model) {
		int result=0;
		try {
			String mobile = req.getParameter("mobile");
			String name = req.getParameter("name");
			String passcode = req.getParameter("passcode");
			String gender = req.getParameter("gender");
			String birthday = req.getParameter("birthday");
			String school = req.getParameter("school");
			String email = req.getParameter("email");
			String address = req.getParameter("address");
			String member_id = req.getParameter("member_id");
		
			int mid = Integer.parseInt(member_id);
			result = _mem.update(mobile, name, passcode, gender, birthday, school, email, address, mid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		return ""+result;
	}
}
