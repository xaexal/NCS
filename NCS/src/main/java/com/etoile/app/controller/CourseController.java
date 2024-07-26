package com.etoile.app.controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Course;
import com.etoile.app.DTO.Course;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CourseController {
	@Autowired _Course _crs;
	
	@PostMapping("/courseListAll")
	public String courseListAll(HttpServletRequest req) {
		LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        System.out.println(formattedDate);
        if(req.getParameter("member_id")!=null) {
        	ArrayList<Course> alCourse = _crs.courseListAll();
        	System.out.println("alCourse size="+alCourse.size());
        	JSONArray ja = new JSONArray();
        	for(Course x : alCourse) {
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
        	}
            return ja.toJSONString();
        }
	}
	
}
