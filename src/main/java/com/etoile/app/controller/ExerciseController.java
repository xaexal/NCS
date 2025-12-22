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

import com.etoile.app.DAO._Exercise;
import com.etoile.app.DTO.Exercise;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins="http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/exercise")
public class ExerciseController {
	@Autowired _Exercise _exr;

	@GetMapping("/list/{course}")
	public String doList(@PathVariable("course") int course) {
	    try {
			ArrayList<Exercise> arExercise = _exr.list(course);
			System.out.println("arExercise size="+arExercise.size());
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
	@PostMapping("/")
	public String doAdd(@RequestBody Map<String,String> req) {
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		try {
			int n=0;
			String eid=req.get("eid");
			if(eid==null || eid.equals("")) {
				n = _exr.insert(Integer.parseInt(req.get("cid")),
							Integer.parseInt(req.get("did")));
			} else {
				n = _exr.update(Integer.parseInt(req.get("cid")),
							Integer.parseInt(req.get("did")),
							Integer.parseInt(eid));
			}
			return "ok";
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
	@DeleteMapping("/{eid}")
	public String doDelete(@PathVariable("eid") int eid) {
		try {
			System.out.println("eid ["+eid+"]");
			int n = _exr.delete(eid);
			return "ok";
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