package com.etoile.app.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Course;
import com.etoile.app.DAO._Drilltype;
import com.etoile.app.DAO._Member;
import com.etoile.app.DTO.Course;
import com.etoile.app.DTO.Drilltype;
import com.etoile.app.DTO.Member;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins="http://localhost:5173",allowCredentials="true")
@RestController
public class HomeController {
	@Autowired _Member _mem;
	@Autowired _Course _crs;
	@Autowired _Drilltype _dt;
	@Autowired private JavaMailSender mailSender;

	@GetMapping("/")
	public String home(HttpSession s) {
		try {
		} catch(Exception e) {

		}
//		return "home";
		return "login";
	}
	@GetMapping("/checkLogin")
	public String login(HttpSession s) {
		String n = null;
		try {
			System.out.println("member_id ["+s.getAttribute("member_id")+"]");
			System.out.println("level ["+s.getAttribute("level")+"]");
			if(s.getAttribute("member_id")==null) n = "0";	// not loggin yet
			else if((Integer)s.getAttribute("level")==0) n = "1";	// administrator or instructor
			else n = "2";	// student or member
			System.out.println("checkLogin "+n);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			n = "0";
		}
		return n;
	}
	@PostMapping("/signup")
	public String signup(@RequestBody Map<String,String> req) {
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		int result=0;
		try {
			Member member = new Member();
			member.setMobile(req.get("mobile"));
			member.setPasscode(req.get("passcode"));
			result = _mem.insert(member);
			if(result==1) return "login";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "signup";
	}
	@PostMapping("/login")
	public Member checkUser(@RequestBody Map<String,String> req,HttpSession s) {
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		System.out.println("/checkUser");
		try {
			String mobile = req.get("mobile");
			String passcode = req.get("passcode");
			System.out.println("["+mobile+","+passcode+"]");
			Member member = _mem.checkUser(mobile, passcode);
			if(member==null) {
//				m.addAttribute("msg","모바일번호/비밀번호가 잘못 입력됐거나 회원가입한 적이 없습니다");
//				return "/login";
				return null;
			}
			System.out.println(member.getName());
			s.setAttribute("member_id", member.getMid());
			s.setAttribute("mobile", req.get("mobile"));
			s.setAttribute("name", member.getName());
			s.setAttribute("level", member.getLevel());

			int n = _mem.saveLoginTime(mobile);
			System.out.println("n="+n);
			if(n<1) {

			}
//			if(member.getName()==null) member.setName("");
			return member;
//			String name = member.getName();
//			System.out.println("member_name ["+name+"]");
//			if(name==null || name.equals("")) {
//				return "/personal";
//			}
//			System.out.println("level ["+member.getLevel()+"]");
//			if(member.getLevel()==0) { // 관리자 레벨
//				return "/drillViewT";
//			}
//			System.out.println("member_id ["+member.getMid()+"]");
//			System.out.println("student count ["+_mem.checkStudent(member.getMid())+"]");
//			if( _mem.checkStudent(member.getMid())>0 ) {
//				return "/drillViewS";
//			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
//			return "/login";
			return null;
		}
//		m.addAttribute("title","프로그래밍 연습");
//		return "/courseS";
	}
	@GetMapping("/passcode")
	public String doPasscode(HttpSession s) {
//		m.addAttribute("member_id",s.getAttribute("member_id"));
		return "redirect:/ncs/passcode";
	}
//	@GetMapping("/change_passcode")
//	public String changePasscode(@RequestBody Map<String,String> req, HttpSession s) {
//		Member member = _mem.checkUser((String)s.getAttribute("mobile"), (String)s.getAttribute("passcode"));
//		if(member==null) {
//			m.addAttribute("member_id",req.get("member_id"));
//			m.addAttribute("msg","비밀번호가 일치하지 않습니다.");
//			return "ncs/passcode";
//		} else {
//			int n = _mem.changePasscode(req.get("member_id"),req.get("passcode"),
//					req.get("passcode_new"));
//			return "redirect:/ncs";
//		}
//
//	}
	@GetMapping("/logout")
	public String doLogout(HttpSession s) {
		try {
			s.invalidate();
			System.out.println("logout");
			return "ok";
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "fail";
		}
	}
	@GetMapping("/personal")
	public Member doPersonal(@RequestBody Map<String,String> req,HttpSession s) {
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		try {
			System.out.println("/personal");
			String mobile = (String)s.getAttribute("mobile");
			System.out.println("mobile ["+mobile+"]");
			if(mobile==null || mobile.equals("")) throw new Exception("You should log in.");

			Member m=_mem.get(mobile);
			return m;
//			model.addAttribute("member",m);
//			return "personal";
		} catch (Exception e) {
			System.out.println(e.getMessage());
//			return "redirect:/login";
			return null;
		}
	}
	@GetMapping("/findPassword")
	public String findPassword(@RequestBody Map<String,String> req) {
		return "findPassword";
	}
	@PostMapping("/sendPasscode")
	@ResponseBody
	public String sendPasscode(@RequestBody Map<String,String> req) {
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		try {
			String mobile = req.get("mobile");
			String email = req.get("email");

			String newPass = this.tempPasscode(8);
			System.out.println("newPass ["+newPass+"]");

	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(email);
	        message.setSubject("프로그래밍 연습 로그인용 임시비밀번호");
	        message.setText(newPass);
	        message.setFrom("cavenagh@naver.com");  // 보내는 이메일 주소 (필요에 따라 생략 가능)
	        mailSender.send(message);
			int result = _mem.setTempPasscode(mobile, newPass);
			System.out.println("result="+result);
			if( result!= 1 ) return "redirect:/findPassword";
			return "0";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "-1";
	}
	private String tempPasscode(int length) {
        // 알파벳(대소문자), 숫자, 특수문자 정의
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }

        return sb.toString();
    }
	@GetMapping("/changePassword")
	public String changePassword() {
		return "changePassword";
	}
	@PostMapping("/passwordChange")
	public String passwordChange(@RequestBody Map<String,String> req) {
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		try {
			String mobile = req.get("mobile");
			String passcode = req.get("passcode");
			String passcode1 = req.get("passcode1");

			if(_mem.changePasscode(mobile, passcode, passcode1)==1) {
				return "redirect:/login";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return "redirect:/changePassword";
	}
}
