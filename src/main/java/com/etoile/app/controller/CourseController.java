package com.etoile.app.controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Course;
import com.etoile.app.DAO._Student;
import com.etoile.app.DTO.Course;
import com.etoile.app.DTO.Student;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/course")
public class CourseController {
	@Autowired _Course _crs;
	@Autowired _Student _std;

	@PostMapping("/list")
	public String getList() {
		ArrayList<Course> alCourse = _crs.listAll();
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

	@PostMapping({"/applicable","/applied","/enrolled",
				  "/completed","/listAll"})
	public String doList(@RequestBody Map<String, Object> req) {
        ArrayList<Course> alCourse = null;
		String mid = req.get("member_id").toString();
		int member_id=0;
		if(mid != null && !mid.equals("")) member_id=Integer.parseInt(mid);
		System.out.println("mid ["+member_id+"]");

		String requestURI = req.getRequestURI();
		if(requestURI.endsWith("listAll")){
//			LocalDate today = LocalDate.now();
//	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//	        String formattedDate = today.format(formatter);
//	        System.out.println(formattedDate);
        	alCourse = _crs.listAll();
		} else if(requestURI.endsWith("applicable")) {
			alCourse = _crs.applicable(member_id);
		} else if(requestURI.endsWith("applied")) {
			alCourse = _crs.applied(member_id);
		} else if(requestURI.endsWith("enrolled")) {
			alCourse = _crs.enrolled(member_id);
		} else if(requestURI.endsWith("completed")) {
			alCourse = _crs.completed(member_id);
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
		Map<String, String[]> parameterMap =req.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
	        String paramName = entry.getKey();
	        String[] paramValues = entry.getValue();
	        // 요청 매개변수 값에 대해 원하는 작업을 수행합니다.
	        for (String paramValue : paramValues) {
	            System.out.println(paramName+" ["+ paramValue+"]");
	        }
	    }
		String cid = req.getParameter("cid");
		int result=0;
		if(cid == null || cid.equals("")) {
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
		try {
			String cid = req.getParameter("cid");
			if(cid==null || cid.equals("")) throw new Exception("course id is not given");

			ArrayList<Student> arStudent = _std.list(Integer.parseInt(cid));
			if(arStudent.size()>0) throw new Exception("This course has one or more students.");

			result=_crs.delete(Integer.parseInt(cid));
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
}