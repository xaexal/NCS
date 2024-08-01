package com.etoile.app.controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Exercise;
import com.etoile.app.DTO.Exercise;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
	@Autowired _Exercise _dl;
	
	@PostMapping("/list")
	public String doList(HttpServletRequest req) {
		try {
			int cid = Integer.parseInt(req.getParameter("cid"));
			ArrayList<Exercise> arExercise = _dl.list(cid);
			System.out.println("arExercise size="+arExercise.size());
			JSONArray ja = new JSONArray();
			arExercise.forEach(x->{
				JSONObject jo = new JSONObject();
				jo.put("dlid", x.getDlid());
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
	public String doAdd() {
		
	}
	@PostMapping("/delete")
	public String doDelete() {
		
	}
}
