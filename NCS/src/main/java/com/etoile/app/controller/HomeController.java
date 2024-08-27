package com.etoile.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.etoile.app.Entity.Course;
import com.etoile.app.Entity.Drilltype;
import com.etoile.app.Entity.Member;
import com.etoile.app.Repository._Course;
import com.etoile.app.Repository._Drilltype;
import com.etoile.app.Repository._Member;
import com.etoile.app.Service.CourseSvc;
import com.etoile.app.Service.MemberSvc;
import com.etoile.app.Service.StudentSvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	MemberSvc _mem;
	StudentSvc _std;
	CourseSvc _crs;
	@Autowired _Drilltype _dt;
	
	@GetMapping("/")
	public String home(HttpSession s, Model m) {
		try {
			String mobile=(String)s.getAttribute("mobile");
			if(mobile==null || mobile.equals("")) return "redirect:/login";
			
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
	@GetMapping("/login") 
	public String login(HttpServletRequest req, Model m) {
		m.addAttribute("title","하이미디어 일산");
		return "login"; 
	}
	@GetMapping("/signup")
	public String doSignup(HttpServletRequest req, Model m) {
		m.addAttribute("title","하이미디어 일산");
		return "signup";
	}
	@PostMapping("/memberInsert")
	public String memberInsert(HttpServletRequest req, Model m) {
		int result=0;
		try {
			String mobile = req.getParameter("mobile");
			String passcode = req.getParameter("passcode");
			_mem.save(mobile,passcode);
			return "redirect:/login";
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
			s.setAttribute("title","하이미디어 일산");
			s.setAttribute("member_id", member.getMid());
			s.setAttribute("mobile", req.getParameter("mobile"));
			s.setAttribute("name", member.getName());
			s.setAttribute("level", member.getLevel());
			
			_mem.saveLoginTime(mobile);
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
			if( _std.countAsStudent(member.getMid())>0 ) {
				return "redirect:/drillViewS";
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/login";
		}
		m.addAttribute("title","하이미디어 일산");
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
			if(mobile==null || mobile.equals("")) throw new Exception("You shoud log in."); 
			
			ArrayList<Course> alCourse = _crs.listAll();
			System.out.println("alCourse size="+alCourse.size());
			
			model.addAttribute("Courses",alCourse);
			model.addAttribute("title","하이미디어 일산");			return "drillViewT";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		return "redirect:/login";
	}
	@GetMapping("/drillViewS")
	public String doStudentView(HttpServletRequest req,HttpSession s,Model model) {
		try {
			String mobile = (String)s.getAttribute("mobile");
			if(mobile==null || mobile.equals("")) throw new Exception("You shoud log in."); 
			
			ArrayList<Course> alCourse = _crs.enrolled((Integer)s.getAttribute("member_id"));
			System.out.println("alCourse size="+alCourse.size());
			
			model.addAttribute("Courses",alCourse);
			model.addAttribute("title","하이미디어 일산");
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
			if(mobile==null || mobile.equals("")) throw new Exception("You shoud log in.");
			
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
			if(mobile==null || mobile.equals("")) throw new Exception("You shoud log in.");
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
			if(mobile==null || mobile.equals("")) throw new Exception("You shoud log in.");
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
			if(mobile==null || mobile.equals("")) throw new Exception("You shoud log in.");
			
			Member m=_mem.get(mobile);
			model.addAttribute("member",m);
			return "personal";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/login";
		}
	}
}
