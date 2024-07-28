package com.etoile.app.controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.coyote.RequestGroupInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Course;
import com.etoile.app.DTO.Course;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CourseController {
	@Autowired _Course _crs;
	
	@PostMapping({"/courseListAll","/courseListApplied","/courseListUnenrolled",
				  "/courseListPresent","/courseListComplete"})
	public String courseList(HttpServletRequest req) {
        ArrayList<Course> alCourse = null;
		String requestURI = req.getRequestURI();
		if(requestURI.endsWith("All")){
			LocalDate today = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String formattedDate = today.format(formatter);
	        System.out.println(formattedDate);
	        String mid = req.getParameter("member_id");
	        if(mid == null || mid.equals("")) { 
	        	alCourse = _crs.courseListAll();
	        } else {
	        	alCourse = _crs.courseListUnenrolled(Integer.parseInt(mid));
	        }
		} else {
			String mid = req.getParameter("member_id");
			int member_id=0;
			if(mid != null && !mid.equals("")) member_id=Integer.parseInt(mid);
			System.out.println("mid ["+member_id+"]");
			
			if(requestURI.endsWith("Applied")) {
				alCourse = _crs.courseListApplied(member_id);
			} else if(requestURI.endsWith("Present")) {
				alCourse = _crs.courseListPresent(member_id);
			} else if(requestURI.endsWith("Complete")) {
				alCourse = _crs.courseListComplete(member_id);
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
	@PostMapping("/addCourse")
	public String addCourse(HttpServletRequest req) {
		String cid = req.getParameter("cid");
		int result=0;
		if(cid == null || !cid.equals("")) {
			result=_crs.insertCourse(req.getParameter("title"), req.getParameter("period1"), 
					req.getParameter("period2"), Integer.parseInt(req.getParameter("seat_cnt")), 
					Integer.parseInt(req.getParameter("col_cnt")), req.getParameter("alive"), 
					req.getParameter("orgname"));
		} else {
			result=_crs.updateCourse(req.getParameter("title"), req.getParameter("period1"), 
					req.getParameter("period2"), Integer.parseInt(req.getParameter("seat_cnt")), 
					Integer.parseInt(req.getParameter("col_cnt")), req.getParameter("alive"), 
					req.getParameter("orgname"),Integer.parseInt(cid));
		}
		return ""+result;
	}
	@PostMapping("/delCourse")
	public String delCourse(HttpServletRequest req) {
		int result=0;
		String cid = req.getParameter("cid");
		if(cid==null || !cid.equals("")) {
			
		} else {
			result=_crs.deleteCourse(Integer.parseInt(cid));
		}
		return ""+result;
	}
}
