package com.etoile.app.controller;
import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Course;
import com.etoile.app.DAO._Student;
import com.etoile.app.DTO.Course;
import com.etoile.app.DTO.Student;


@CrossOrigin(origins="http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/course")
public class CourseController {
	@Autowired _Course _crs;
	@Autowired _Student _std;

	@GetMapping("/list")
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

		String requestURI = null; //req.getRequestURI();
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
	public String doAdd(@RequestBody Map<String,String> req) {
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		String cid = req.get("cid");
		int result=0;
		if(cid == null || cid.equals("")) {
			result=_crs.insert(req.get("title"), req.get("period1"),
					req.get("period2"), Integer.parseInt(req.get("seat_cnt")),
					Integer.parseInt(req.get("col_cnt")), req.get("alive"),
					req.get("orgname"));
		} else {
			result=_crs.update(req.get("title"), req.get("period1"),
					req.get("period2"), Integer.parseInt(req.get("seat_cnt")),
					Integer.parseInt(req.get("col_cnt")), req.get("alive"),
					req.get("orgname"),Integer.parseInt(cid));
		}
		return ""+result;
	}
	@DeleteMapping("/delete/{cid}")
	public String doDelete(@PathVariable("cid") int cid) {
		int result=0;
		try {
			ArrayList<Student> arStudent = _std.list(cid,null);
			if(arStudent.size()>0) throw new Exception("This course has one or more students.");

			int n=_crs.delete(cid);
			return "ok";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}
	@GetMapping("/get/{cid}")
	public String get(@PathVariable("cid") int cid) {

		try {
			Course x = _crs.get(cid);

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
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
}