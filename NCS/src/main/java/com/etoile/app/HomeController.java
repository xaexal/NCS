package com.etoile.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.etoile.app.DAO._Member;
import com.etoile.app.DTO.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired _Member dMember;
	
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
	public String checkUser(HttpServletRequest req,Model m) {
		String mobile = req.getParameter("mobile");
		String passcode = req.getParameter("passcode");
		Member member = dMember.checkUser(mobile, passcode); 
		if(member==null) {
			m.addAttribute("title","하이미디어 일산");
			m.addAttribute("msg","모바일번호/비밀번호가 잘못 입력됐거나 회원가입한 적이 없습니다");
			return "login";
		}
		HttpSession s = req.getSession();
		s.setAttribute("member_id", member.getMid());
		s.setAttribute("mobile", req.getParameter("mobile"));
		s.setAttribute("name", member.getName());
		s.setAttribute("level", member.getLevel());
		
		int n = dMember.saveLoginTime(mobile);
		if(n<1) {
			
		}
		String name = member.getName();
		System.out.println("member_name ["+name+"]");
		if(name==null || name.equals("")) {
			return "/personal";
		}
		if( member.getLevel()==0) { // 관리자 레벨
			return "/drillViewT";
		}
		if()
	}
}
