package com.etoile.app.controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Student;
import com.etoile.app.DTO.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired _Student _std;
	
	@PostMapping("/list")
	public String doList(HttpServletRequest req) {
		try {
			int cid = Integer.parseInt(req.getParameter("cid"));
			ArrayList<Student> arStudent = _std.list(cid);
			System.out.println("arStudent size="+arStudent.size());
			
			JSONArray ja = new JSONArray();
			arStudent.forEach(x->{{
				JSONObject jo = new JSONObject();
				jo.put("sid", x.getSid());
				jo.put("name", x.getName());
				jo.put("gender", x.getGender());
				jo.put("mobile", x.getMobile());
				jo.put("birthday", x.getBirthday());
				jo.put("seq",x.getSeq());
				jo.put("active", x.getActive());
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
			Student x = _std.get(sid);

			JSONObject jo = new JSONObject();
			jo.put("sid", x.getSid());
			jo.put("name", x.getName());
			jo.put("gender", x.getGender());
			jo.put("mobile", x.getMobile());
			jo.put("birthday", x.getBirthday());
			jo.put("seq",x.getSeq());
			jo.put("active", x.getActive());
			jo.put("member_id", x.getMember_id());
			return jo.toJSONString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
	@PostMapping("/add")	// apply2Course() 
	public String doAdd(HttpServletRequest req) {
		int result=-1;
		try {
			String sid = req.getParameter("sid");
			if(sid==null || sid.equals("")) {
				result = _std.insert(Integer.parseInt(req.getParameter("mid")), 
									 Integer.parseInt(req.getParameter("cid")));
			} else {
				result = _std.update(Integer.parseInt(req.getParameter("mid")), 
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
			result = _std.delete(mid, cid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}
	@PostMapping("/getSID")
	public String getSID(HttpServletRequest req) {
		int sid = -1;
		try {
			int mid = Integer.parseInt(req.getParameter("mid"));
			int cid = Integer.parseInt(req.getParameter("cid"));
			sid = _std.getSID(mid, cid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+sid;
	}
}