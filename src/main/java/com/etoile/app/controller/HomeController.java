package com.etoile.app.controller;

import java.security.SecureRandom;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etoile.app.DAO._Course;
import com.etoile.app.DAO._Drilltype;
import com.etoile.app.DAO._Member;
import com.etoile.app.DTO.Course;
import com.etoile.app.DTO.Drilltype;
import com.etoile.app.DTO.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired _Member _mem;
	@Autowired _Course _crs;
	@Autowired _Drilltype _dt;
	@Autowired private JavaMailSender mailSender;

	@GetMapping("/")
	public String home(HttpSession s, Model m) {
		try {
		} catch(Exception e) {

		}
//		return "home";
		return "redirect:/login";
	}
	@GetMapping("/login")
	public String login(HttpServletRequest req,HttpSession s,  Model m) {
		m.addAttribute("title","프로그래밍 연습");
		try {
			String mobile=(String)s.getAttribute("mobile");
			if(mobile==null || mobile.equals("")) return "login";

			int level=-1;
			Object oLevel = s.getAttribute("level");
			if(oLevel instanceof Integer) level = (Integer) oLevel;
			else if(oLevel instanceof String) level = Integer.parseInt((String) oLevel);

			if(level==0) {
				return "redirect:/drillViewT";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/drillViewS";
	}
	@GetMapping("/signup")
	public String doSignup(HttpServletRequest req, Model m) {
		m.addAttribute("title","프로그래밍 연습");
		return "signup";
	}
	@PostMapping("/memberInsert")
	public String memberInsert(HttpServletRequest req, Model m) {
		int result=0;
		try {
			String mobile = req.getParameter("mobile");
			String passcode = req.getParameter("passcode");
			result = _mem.insert(mobile, passcode);
			if(result==1) return "redirect:/login";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/signup";
	}
	@PostMapping("/checkuser")
	public String checkUser(HttpServletRequest req,HttpSession s,Model m) {
		try {
			String mobile = req.getParameter("mobile");
			String passcode = req.getParameter("passcode");
			System.out.println("["+mobile+","+passcode+"]");
			Member member = _mem.checkUser(mobile, passcode);
			if(member==null) {
				m.addAttribute("msg","모바일번호/비밀번호가 잘못 입력됐거나 회원가입한 적이 없습니다");
				return "login";
			}
			s.setAttribute("title","프로그래밍 연습");
			s.setAttribute("member_id", member.getMid());
			s.setAttribute("mobile", req.getParameter("mobile"));
			s.setAttribute("name", member.getName());
			s.setAttribute("level", member.getLevel());

			int n = _mem.saveLoginTime(mobile);
			System.out.println("n="+n);
			if(n<1) {

			}
			String name = member.getName();
			System.out.println("member_name ["+name+"]");
			if(name==null || name.equals("")) {
				return "redirect:/personal";
			}
			System.out.println("level ["+member.getLevel()+"]");
			if(member.getLevel()==0) { // 관리자 레벨
				return "redirect:/drillViewT";
			}
			System.out.println("member_id ["+member.getMid()+"]");
			System.out.println("student count ["+_mem.checkStudent(member.getMid())+"]");
			if( _mem.checkStudent(member.getMid())>0 ) {
				return "redirect:/drillViewS";
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/login";
		}
		m.addAttribute("title","프로그래밍 연습");
		return "courseS";
	}
	@GetMapping("/passcode")
	public String doPasscode(HttpSession s, Model m) {
		m.addAttribute("member_id",s.getAttribute("member_id"));
		return "ncs/passcode";
	}
//	@GetMapping("/change_passcode")
//	public String changePasscode(HttpServletRequest req, HttpSession s, Model m) {
//		Member member = _mem.checkUser((String)s.getAttribute("mobile"), (String)s.getAttribute("passcode"));
//		if(member==null) {
//			m.addAttribute("member_id",req.getParameter("member_id"));
//			m.addAttribute("msg","비밀번호가 일치하지 않습니다.");
//			return "ncs/passcode";
//		} else {
//			int n = _mem.changePasscode(req.getParameter("member_id"),req.getParameter("passcode"),
//					req.getParameter("passcode_new"));
//			return "redirect:/ncs";
//		}
//
//	}
	@GetMapping("/logout")
	public String doLogout(HttpServletRequest req,HttpSession s,Model m) {
		s.invalidate();
		return "redirect:/";
	}
	@GetMapping("/drillViewT")
	public String doTeacherView(HttpServletRequest req,HttpSession s,Model model) {
		try {
			String mobile = (String)s.getAttribute("mobile");
			if(mobile==null || mobile.equals("")) throw new Exception("You should log in.");

			ArrayList<Course> alCourse = _crs.listAll();
			System.out.println("alCourse size="+alCourse.size());

			model.addAttribute("Courses",alCourse);
			model.addAttribute("title","프로그래밍 연습");			return "drillViewT";
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return "redirect:/login";
	}
	@GetMapping("/drillViewS")
	public String doStudentView(HttpServletRequest req,HttpSession s,Model model) {
		try {
			String mobile = (String)s.getAttribute("mobile");
			if(mobile==null || mobile.equals("")) throw new Exception("You should log in.");

			ArrayList<Course> alCourse = _crs.enrolled((Integer)s.getAttribute("member_id"));
			System.out.println("alCourse size="+alCourse.size());

			model.addAttribute("Courses",alCourse);
			model.addAttribute("title","프로그래밍 연습");
			return "drillViewS";
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return "redirect:/login";
	}
	@GetMapping("/drillT")
	public String doDrillT(HttpServletRequest req,HttpSession s,Model model) {
		try {
			String mobile = (String)s.getAttribute("mobile");
			if(mobile==null || mobile.equals("")) throw new Exception("You should log in.");

			ArrayList<Drilltype> arDrilltype = _dt.list();
			System.out.println("arDrilltype size="+arDrilltype.size());
			model.addAttribute("arDrillType",arDrilltype);
			ArrayList<Course> arCourse = _crs.listAll();
			System.out.println("arCOurse size="+arCourse.size());
			model.addAttribute("courses",arCourse);
			return "drillT";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/login";
		}
	}
	@GetMapping("/courseS")
	public String doCourseS(HttpServletRequest req,HttpSession s,Model model) {
		try {
			String mobile = (String)s.getAttribute("mobile");
			if(mobile==null || mobile.equals("")) throw new Exception("You should log in.");
			return "courseS";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/login";
		}
	}
	@GetMapping("/courseT")
	public String doCourseT(HttpServletRequest req,HttpSession s,Model model) {
		try {
			String mobile = (String)s.getAttribute("mobile");
			if(mobile==null || mobile.equals("")) throw new Exception("You should log in.");
			return "courseT";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/login";
		}
	}
	@GetMapping("/personal")
	public String doPersonal(HttpServletRequest req,HttpSession s,Model model) {
		try {
			String mobile = (String)s.getAttribute("mobile");
			System.out.println("mobile ["+mobile+"]");
			if(mobile==null || mobile.equals("")) throw new Exception("You should log in.");

			Member m=_mem.get(mobile);
			model.addAttribute("member",m);
			return "personal";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/login";
		}
	}
	@GetMapping("/findPassword")
	public String findPassword(HttpServletRequest req,Model model) {
		return "findPassword";
	}
	@PostMapping("/sendPasscode")
	@ResponseBody
	public String sendPasscode(HttpServletRequest req, Model model) {
		try {
			String mobile = req.getParameter("mobile");
			String email = req.getParameter("email");

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
	public String passwordChange(HttpServletRequest req) {
		try {
			String mobile = req.getParameter("mobile");
			String passcode = req.getParameter("passcode");
			String passcode1 = req.getParameter("passcode1");

			if(_mem.changePasscode(mobile, passcode, passcode1)==1) {
				return "redirect:/login";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return "redirect:/changePassword";
	}
}
