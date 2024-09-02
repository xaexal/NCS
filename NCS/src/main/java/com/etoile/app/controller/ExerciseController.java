package com.etoile.app.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.Entity.Exercise;
import com.etoile.app.Service.ExerciseSvc;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
	private final ExerciseSvc _exr;
	
	ExerciseController(){
		this._exr = new ExerciseSvc();
	}
	@PostMapping("/list")
	public String doList(HttpServletRequest req) {
		try {
			int cid = Integer.parseInt(req.getParameter("cid"));
			List<Exercise> arExercise = _exr.list(cid);
//			System.out.println("arExercise size="+arExercise.size());
			JSONArray ja = new JSONArray();
			arExercise.forEach(x->{
				JSONObject jo = new JSONObject();
				jo.put("eid", x.getEid());
				jo.put("did", x.getDid());
				jo.put("dtype_id", x.getDtype_id());
				jo.put("name", x.getName());
				jo.put("comment", x.getComment());
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
	@PostMapping("/add")
	public String doAdd(HttpServletRequest req) {
		boolean result=false;
		try {
			String eid=req.getParameter("eid");
			if(eid==null || eid.equals("")) {
				result = _exr.insert(Integer.parseInt(req.getParameter("cid")), 
							Integer.parseInt(req.getParameter("did")));
			} else {
				result = _exr.update(Integer.parseInt(req.getParameter("cid")), 
							Integer.parseInt(req.getParameter("did")),
							Integer.parseInt(eid));
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		if(result) return "0";
		else return "-1";
	}
	@PostMapping("/delete")
	public String doDelete(HttpServletRequest req) {
		boolean result=false;
		try {
			int cid = Integer.parseInt(req.getParameter("cid"));
			result = _exr.delete(cid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(result) return "0";
		else return "-1";
	}
	@PostMapping("/lastCreated")
	public String lastCreated(HttpServletRequest req) {
		String lastTime="";
		try {
			int cid = Integer.parseInt(req.getParameter("cid"));
			lastTime = _exr.lastCreated(cid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lastTime;
	}

}