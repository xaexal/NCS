package com.etoile.app.controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Exercise;
import com.etoile.app.DTO.Exercise;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/exercise")
public class ExerciseController {
	@Autowired _Exercise _exr;
	
	@PostMapping("/list")
	public String doList(HttpServletRequest req) {
		try {
			int cid = Integer.parseInt(req.getParameter("cid"));
			ArrayList<Exercise> arExercise = _exr.list(cid);
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
		try {
			int n=0;
			String eid=req.getParameter("eid");
			if(eid==null || eid.equals("")) {
				n = _exr.insert(Integer.parseInt(req.getParameter("cid")), 
							Integer.parseInt(req.getParameter("did")));
			} else {
				n = _exr.update(Integer.parseInt(req.getParameter("cid")), 
							Integer.parseInt(req.getParameter("did")),
							Integer.parseInt(eid));
			}
			return ""+n;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
	@PostMapping("/delete")
	public String doDelete(HttpServletRequest req) {
		try {
			int cid = Integer.parseInt(req.getParameter("cid"));
			int n = _exr.delete(cid);
			return ""+n;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
		
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