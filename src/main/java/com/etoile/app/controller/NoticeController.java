package com.etoile.app.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Member;
import com.etoile.app.DAO._Notice;
import com.etoile.app.DTO.Notice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/notice")
public class NoticeController {
	@Autowired _Notice ndao;
	@Autowired _Member _mem;
	
	@GetMapping("/list")
	public String getList(HttpServletRequest req,Model m) {
		try {
			System.out.println("/notice/list");

			String pageno=req.getParameter("pageno");
			int start=0;
			int pagesize=20;
			if(pageno==null || pageno.equals("")) {
				pageno="1";
			}
			start=(Integer.parseInt(pageno)-1)*pagesize;
			ArrayList<Notice> arZero = ndao.selectByLevel(0,0,10);
			int nPost=arZero.size();
			ArrayList<Notice> arFirst = ndao.selectByLevel(10,0,10);
			nPost+=arFirst.size();
			pagesize-=nPost;
			ArrayList<Notice> arNotice = ndao.selectByLevel(20,start,pagesize);
//			System.out.println("arZero size="+arZero.size());
//			System.out.println("arFirst size="+arFirst.size());
//			System.out.println("arNotice size="+arNotice.size());
			m.addAttribute("zero",arZero);
			m.addAttribute("first",arFirst);
			m.addAttribute("arNotice",arNotice);
			m.addAttribute("start",start);
			m.addAttribute("NoticeTitle","공지사항 목록");
			return "notice/list";
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/";
	}
	@GetMapping("/write")
	public String doWrite(HttpServletRequest req, HttpSession s, Model m) {
		System.out.println("writeNotice");
		try {
			if(s.getAttribute("level")==null || (Integer)s.getAttribute("level")!=0) {
				return "redirect:/member/signin";
			}
			int member_id=Integer.parseInt(String.valueOf(s.getAttribute("member_id")));
			String member_name="";//_mem.getName(member_id);
			System.out.println("member_name="+member_name);
			m.addAttribute("name",member_name);
			m.addAttribute("mode","new");
			m.addAttribute("NoticeTitle","공지사항 작성");
			return "notice/write";
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "rediredt:/";
	}
	@PostMapping("/save")
	@ResponseBody
	public String doSave(HttpServletRequest req,HttpSession s) {
		JSONObject result = new JSONObject();
		result.put("errcode",Errata.error);
		result.put("data",null);
		try {
			Map<String, String[]> parameterMap =req.getParameterMap();
			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
		        String paramName = entry.getKey();
		        String[] paramValues = entry.getValue();
		        for (String paramValue : paramValues) {
		            System.out.println(paramName+" ["+paramValue+"]");
		        }
			}
			if(s.getAttribute("member_id")==null) {
				result.put("errcode", Errata.signin);
				result.put("data","/signin");
				throw new Exception( Message.signIn);
			}
			String title=req.getParameter("title");
			String content=req.getParameter("content");
			String level=req.getParameter("level");
			String member_id=req.getParameter("member_id");
			String id = req.getParameter("id");
			int memberID = Integer.parseInt(String.valueOf(s.getAttribute("member_id")));
			int n=0, lastID=-1;
			if(id==null || id.equals("")) {
				n=ndao.insert(title, content, memberID, level, 0);
				lastID = ndao.getLast(memberID);
			} else {
				n=ndao.update(title, content, memberID, level, n, Integer.parseInt(id));
				lastID=Integer.parseInt(id);
			}
			result.put("errcode",Errata.none);
			result.put("message",null);
			result.put("data", lastID);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			result.put("message", e.getMessage());
		}
		return result.toJSONString();
	}
	
	@GetMapping("/view")
	public String doView(HttpServletRequest req, HttpSession s, Model m) {
		try {
//			if(s.getAttribute("member_id")==null) {
//				return "redirect:/member/signin";
//			}
			String id = req.getParameter("id");
			System.out.println("id="+id);
			if(id==null || id.equals("")) return "redirect:/notice/list";
			
			Notice ndto=ndao.selectOne(Integer.parseInt(id));
			m.addAttribute("notice",ndto);
			m.addAttribute("mode","view");
			m.addAttribute("NoticeTitle","공지사항");
			ndao.addHit(Integer.parseInt(id));
			return "notice/write";
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/";
	}
	@GetMapping("/update")
	public String doUpdate(HttpServletRequest req, HttpSession s, Model m) {
		try {
			if(s.getAttribute("member_id")==null) {
				return "redirect:/member/signin";
			}
			String id = req.getParameter("id");
			if(id==null || id.equals("")) return "redirect:/notice/list";
			
			Notice ndto=ndao.selectOne(Integer.parseInt(id));
			m.addAttribute("notice",ndto);
			m.addAttribute("mode","update");
			m.addAttribute("NoticeTitle","공지사항 변경");
			return "notice/write";
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/";
	}
	@PostMapping("/delete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, HttpSession s, Model m) {
		Map<String, String[]> parameterMap =req.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
	        String paramName = entry.getKey();
	        String[] paramValues = entry.getValue();
	        for (String paramValue : paramValues) {
	            System.out.println(paramName+" ["+paramValue+"]");
	        }
		}
		JSONObject result = new JSONObject();
		result.put("errcode",Errata.error);
		result.put("data",null);
		try {
			if(s.getAttribute("member_id")==null){
				result.put("errcode", Errata.signin);
				result.put("data","/signin");
				throw new Exception( Message.signIn);
			}
			String id = req.getParameter("id");
			if(id==null || id.equals("")) {
				result.put("errcode", Errata.error);
				throw new Exception("존재하지 않는 게시물번호 입니다");
			}

			int n = ndao.delete(Integer.parseInt(id));
			if(n==1) {
				result.put("errcode", Errata.none);
				result.put("message", "삭제 성공");
				result.put("data","/"); // 목록으로 돌아가기
				return result.toJSONString();
			}
			throw new Exception( "삭제 실패");
			//result.put("data","/"); 이동하지 않음 
		} catch(Exception e) {
			result.put("message", e.getMessage());
		}
		return result.toJSONString();
	}
}
