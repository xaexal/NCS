package com.etoile.app.controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Course;
import com.etoile.app.DTO.Course;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/course")
public class CourseController {
	@Autowired _Course _crs;
	
	@PostMapping({"/list","/applied","/unenrolled",
				  "/present","/complete"})
	public String doList(HttpServletRequest req) {
        ArrayList<Course> alCourse = null;
		String requestURI = req.getRequestURI();
		if(requestURI.endsWith("list")){
			LocalDate today = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String formattedDate = today.format(formatter);
	        System.out.println(formattedDate);
	        String mid = req.getParameter("member_id");
	        if(mid == null || mid.equals("")) { 
	        	alCourse = _crs.list();
	        } else {
	        	alCourse = _crs.unenrolled(Integer.parseInt(mid));
	        }
		} else {
			String mid = req.getParameter("member_id");
			int member_id=0;
			if(mid != null && !mid.equals("")) member_id=Integer.parseInt(mid);
			System.out.println("mid ["+member_id+"]");
			
			if(requestURI.endsWith("applied")) {
				alCourse = _crs.applied(member_id);
			} else if(requestURI.endsWith("present")) {
				alCourse = _crs.present(member_id);
			} else if(requestURI.endsWith("complete")) {
				alCourse = _crs.complete(member_id);
			}
		}
    	System.out.println("alCourse size="+alCourse.size());
    	JSONArray ja = new JSONArray();
    	alCourse.forEach(x->{
    		JSONObject jo = new JSONObject();
    		jo.put("cid", x.getCid());
    		jo.put("title", x.getTitle());
    		jo.put("orgname", x.getOrgname());
    		jo.put("period1", x.getPeriod1());
    		jo.put("period2", x.getPeriod2());
    		jo.put("days", x.getDays());
    		jo.put("endtime", x.getEndtime());
    		jo.put("alive", x.getAlive());
    		jo.put("seat_cnt", x.getSeat_cnt());
    		jo.put("col_cnt", x.getCol_cnt());
    		jo.put("created", x.getCreated());
    		jo.put("updated", x.getUpdated());
    		ja.add(jo);
    	});
        return ja.toJSONString();
	}
	@PostMapping("/add")
	public String doAdd(HttpServletRequest req) {
		String cid = req.getParameter("cid");
		int result=0;
		if(cid == null || !cid.equals("")) {
			result=_crs.insert(req.getParameter("title"), req.getParameter("period1"), 
					req.getParameter("period2"), Integer.parseInt(req.getParameter("seat_cnt")), 
					Integer.parseInt(req.getParameter("col_cnt")), req.getParameter("alive"), 
					req.getParameter("orgname"));
		} else {
			result=_crs.update(req.getParameter("title"), req.getParameter("period1"), 
					req.getParameter("period2"), Integer.parseInt(req.getParameter("seat_cnt")), 
					Integer.parseInt(req.getParameter("col_cnt")), req.getParameter("alive"), 
					req.getParameter("orgname"),Integer.parseInt(cid));
		}
		return ""+result;
	}
	@PostMapping("/delete")
	public String doDelete(HttpServletRequest req) {
		int result=0;
		String cid = req.getParameter("cid");
		if(cid==null || !cid.equals("")) {
			
		} else {
			result=_crs.delete(Integer.parseInt(cid));
		}
		return ""+result;
	}
	@PostMapping("/get")
	public String get(HttpServletRequest req) {
		String cid = req.getParameter("cid");
		if(cid==null || cid.equals("")) return "";
		
		Course x = _crs.get(Integer.parseInt(cid));
		JSONObject jo = new JSONObject();
		jo.put("cid", x.getCid());
		jo.put("title", x.getTitle());
		jo.put("orgname", x.getOrgname());
		jo.put("period1", x.getPeriod1());
		jo.put("period2", x.getPeriod2());
		jo.put("days", x.getDays());
		jo.put("endtime", x.getEndtime());
		jo.put("alive", x.getAlive());
		jo.put("seat_cnt", x.getSeat_cnt());
		jo.put("col_cnt", x.getCol_cnt());
		jo.put("created", x.getCreated());
		jo.put("updated", x.getUpdated());
		return jo.toJSONString();
	}
	@PostMapping({"/add2Present","/del4Present"})
	public String add2Present(HttpServletRequest req) {
		String sid = req.getParameter("sid"); // 학생ID
		if(sid==null || sid.equals("")) return "-1";
		String requestURI = req.getRequestURI();
		String status="";
		if(requestURI.startsWith("add"))
			status="수강중";
		else if(requestURI.startsWith("del"))
			status="신청";
		int n = _crs.update2Present(Integer.parseInt(sid),status);
		
		return ""+n;
	}
}
