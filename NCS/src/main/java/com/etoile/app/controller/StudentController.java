package com.etoile.app.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.Entity.Student;
import com.etoile.app.Service.StudentSvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/student")
public class StudentController {
	private final StudentSvc stdSvc = new StudentSvc();
	
	@PostMapping("/list")
	public String doList(HttpServletRequest req, HttpSession s) {
		try {
			int cid = Integer.parseInt(req.getParameter("cid"));
			List<Student> arStudent = stdSvc.list(cid);
			System.out.println("arStudent size="+arStudent.size());
			
			JSONArray ja = new JSONArray();
			arStudent.forEach(x->{{
				JSONObject jo = new JSONObject();
				jo.put("sid", x.getSid());
				jo.put("name", x.getMember());
				jo.put("gender", x.());
				jo.put("mobile", x.getMobile());
				jo.put("birthday", x.getBirthday());
				jo.put("seq",x.getSeq());
				jo.put("status", x.getStatus());
				jo.put("member_id", x.getMember_id());
				ja.add(jo);
			}});
			return ja.toJSONString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
	@PostMapping("/get")
	public String doGet(HttpServletRequest req) {
		try {
			int sid = Integer.parseInt(req.getParameter("sid"));
			Student x = stdSvc.get(sid);

			JSONObject jo = new JSONObject();
			jo.put("sid", x.getSid());
			jo.put("name", x.getName());
			jo.put("gender", x.getGender());
			jo.put("mobile", x.getMobile());
			jo.put("birthday", x.getBirthday());
			jo.put("school", x.getSchool());
			jo.put("seq",x.getSeq());
			jo.put("status", x.getStatus());
			jo.put("member_id", x.getMember_id());
			return jo.toJSONString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
	@PostMapping("/add")	// apply2Course() 
	public String doAdd(HttpServletRequest req) {
		boolean result=false;
		try {
			String sid = req.getParameter("sid");
			if(sid==null || sid.equals("")) {
				result = stdSvc.insert(Integer.parseInt(req.getParameter("mid")), 
									 Integer.parseInt(req.getParameter("cid")));
			} else {
				result = stdSvc.update(Integer.parseInt(req.getParameter("mid")), 
									 Integer.parseInt(req.getParameter("cid")),
									 Integer.parseInt(sid));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}
	@PostMapping("/delete")
	public String doDelete(HttpServletRequest req) {
		int result = -1;
		try {
			int mid = Integer.parseInt(req.getParameter("mid"));
			int cid = Integer.parseInt(req.getParameter("cid"));
			result = stdSvc.delete(mid, cid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}
	@PostMapping("/getSID")
	public String getSID(HttpServletRequest req) {
		int sid = -1;
		try {
			int mid = Integer.parseInt(req.getParameter("member_id"));
			int cid = Integer.parseInt(req.getParameter("course_id"));
			sid = stdSvc.getSID(mid, cid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+sid;
	}
}