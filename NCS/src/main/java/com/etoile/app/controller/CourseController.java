package com.etoile.app.controller;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.Entity.Course;
import com.etoile.app.Entity.Student;
import com.etoile.app.Service.CourseSvc;
import com.etoile.app.Service.StudentSvc;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/course")
public class CourseController {
	private final CourseSvc crsSvc = new CourseSvc();
	private final StudentSvc stdSvc= new StudentSvc();

	@PostMapping("/delete")
	public String doDelete(HttpServletRequest req) {
		String result="0";
		try {
			String cid = req.getParameter("cid");
			if(cid==null || cid.equals("")) throw new Exception("course id is not given");
			
			int n = stdSvc.countPerCourse(Integer.parseInt(cid));
			if(n>0) throw new Exception("This course has one or more students.");
			
			result=crsSvc.delete(Integer.parseInt(cid));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}
	@PostMapping({"/applicable","/applied","/enrolled",
				  "/completed","/listAll"})
	public String doList(HttpServletRequest req) {
        List<Course> alCourse = null;
		String mid = req.getParameter("member_id");
		int member_id=0;
		if(mid != null && !mid.equals("")) member_id=Integer.parseInt(mid);
		System.out.println("mid ["+member_id+"]");
		
		String requestURI = req.getRequestURI();
		if(requestURI.endsWith("listAll")){
        	alCourse = crsSvc.listAll();
		} else if(requestURI.endsWith("applicable")) {
			alCourse = crsSvc.listApplicable(member_id);
		} else if(requestURI.endsWith("applied")) {
			alCourse = crsSvc.listByStatus(member_id,"신청");
		} else if(requestURI.endsWith("enrolled")) {
			alCourse = crsSvc.listByStatus(member_id,"수강중");
		} else if(requestURI.endsWith("completed")) {
			alCourse = crsSvc.listByStatus(member_id,"수료");
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
    		jo.put("seat_cnt", x.getSeatCnt());
    		jo.put("col_cnt", x.getColCnt());
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
		String result="0";
		if(cid == null || cid.equals("")) {
			result=crsSvc.insert(req.getParameter("title"), req.getParameter("period1"), 
					req.getParameter("period2"), Integer.parseInt(req.getParameter("seat_cnt")), 
					Integer.parseInt(req.getParameter("col_cnt")), req.getParameter("alive"), 
					req.getParameter("orgname"));
		} else {
			result=crsSvc.update(req.getParameter("title"), req.getParameter("period1"), 
					req.getParameter("period2"), Integer.parseInt(req.getParameter("seat_cnt")), 
					Integer.parseInt(req.getParameter("col_cnt")), req.getParameter("alive"), 
					req.getParameter("orgname"),Integer.parseInt(cid));
		}
		return result;
	}
	@PostMapping("/get")
	public String get(HttpServletRequest req) {
		String cid = req.getParameter("cid");
		if(cid==null || cid.equals("")) return "";
		
		Course x = crsSvc.get(Integer.parseInt(cid));
		JSONObject jo = new JSONObject();
		jo.put("cid", x.getCid());
		jo.put("title", x.getTitle());
		jo.put("orgname", x.getOrgname());
		jo.put("period1", x.getPeriod1());
		jo.put("period2", x.getPeriod2());
		jo.put("days", x.getDays());
		jo.put("endtime", x.getEndtime());
		jo.put("alive", x.getAlive());
		jo.put("seat_cnt", x.getSeatCnt());
		jo.put("col_cnt", x.getColCnt());
		jo.put("created", x.getCreated());
		jo.put("updated", x.getUpdated());
		return jo.toJSONString();
	}
}