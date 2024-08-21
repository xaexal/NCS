package com.etoile.app.controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Status;
import com.etoile.app.DTO.Status;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/status")
public class StatusController {
	@Autowired _Status _ds;
	
	@PostMapping("/list")
	public String doList(HttpServletRequest req) {
		try {
			int eid = Integer.parseInt(req.getParameter("eid"));
			System.out.println("eid ["+eid+"]");
			
			ArrayList<Status> arStatus = _ds.list(eid);
//			System.out.println("arStatus size ["+arStatus.size()+"]");
			JSONArray ja = new JSONArray();
			arStatus.forEach(x->{
				JSONObject jo = new JSONObject();
				jo.put("student_id", x.getStudent_id());
				jo.put("status", x.getStatus());
				ja.add(jo);
			});
			return ja.toJSONString();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
		
	}
	@PostMapping("/list4student")
	public String doList4student(HttpServletRequest req) {
		try {
			int sid = Integer.parseInt(req.getParameter("student_id"));
//			System.out.println("sid ["+sid+"]");
			
			ArrayList<Status> arStatus = _ds.list4Student(sid);
//			System.out.println("arStatus size ["+arStatus.size()+"]");
			JSONArray ja = new JSONArray();
			arStatus.forEach(x->{
				JSONObject jo = new JSONObject();
				jo.put("dsid", x.getDsid());
				jo.put("drill_id", x.getDrill_id());
				jo.put("status", x.getStatus());
				jo.put("student_id", x.getStudent_id());
				jo.put("eid", x.getEid());
				jo.put("created", x.getCreated());
				jo.put("updated", x.getUpdated());
				ja.add(jo);
			});
			return ja.toJSONString();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
		
	}
//	@PostMapping("/get")
//	public String get(HttpServletRequest req) {
//		try {
//			int student_id = Integer.parseInt(req.getParameter("sid"));
//			ArrayList<Status> arStatus = _ds.get(student_id);
//			System.out.println("arStatus size ["+arStatus.size()+"]");
//			JSONArray ja = new JSONArray();
//			arStatus.forEach(x->{
//				JSONObject jo = new JSONObject();
//				jo.put("drill_id", x.getDrill_id());
//				jo.put("status", x.getStatus());
//				ja.add(jo);
//			});
//			return ja.toJSONString();
//		} catch(Exception e) {
//			System.out.println(e.getMessage());
//			return "";
//		}
//	}
	@PostMapping("/update")
	public String doUpdate(HttpServletRequest req,HttpSession s) {
		int result=-1;
		try {
			String mobile = (String)s.getAttribute("mobile");
			if(mobile==null || mobile.equals("")) return "";
			
			int level=-1;
			Object oLevel = s.getAttribute("level");
			if(oLevel instanceof Integer) level = (Integer) oLevel;
			else if(oLevel instanceof String) level = Integer.parseInt((String) oLevel);

			int sid = Integer.parseInt(req.getParameter("sid"));
			int eid = Integer.parseInt(req.getParameter("eid"));
//			System.out.println("level ["+level+"] sid ["+sid+"] eid ["+eid+"]");
			int n = _ds.count(eid,sid);
//			System.out.println("n ["+n+"]");
			String status="작업중";
			if(n==0) {
				n=_ds.insert(eid, sid, status);
			} else {
				status = _ds.get(eid, sid);
			}
			System.out.println("status ["+status+"]");
			if(status.equals("완료")) {
				status = "작업중";
			} else if(status.equals("확인중")) {
				if(level==0) {
					status="완료";
				} else {
					status="작업중";
				}
			} else {
				status="확인중";
			} 
			result = _ds.update(eid, sid, status);
//			System.out.println("result ["+result+"] status ["+status+"]");
			if(result==1) return status;
		} catch (Exception e) {
			System.out.println(e.getMessage());
//			System.out.println("error");
		}
		return "";
	}
	@PostMapping("/lastUpdated")
	public String lastUpdated(HttpServletRequest req) {
		String updated="";
		try {
			int sid = Integer.parseInt(req.getParameter("sid"));
			updated = _ds.lastUpdated(sid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
//		System.out.println("updated ["+updated+"]");
		return updated;
	}
}