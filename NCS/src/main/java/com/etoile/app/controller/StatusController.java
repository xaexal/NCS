package com.etoile.app.controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Status;
import com.etoile.app.DTO.Status;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/status")
public class StatusController {
	@Autowired _Status _ds;
	
	@PostMapping("/list")
	public String doList(HttpServletRequest req) {
		try {
			int drill_id = Integer.parseInt(req.getParameter("did"));
			int cid = Integer.parseInt(req.getParameter("cid"));
			ArrayList<Status> arStatus = _ds.drillStatusAll(drill_id,cid);
			System.out.println("arStatus size ["+arStatus.size()+"]");
			JSONArray ja = new JSONArray();
			arStatus.forEach(x->{
				JSONObject jo = new JSONObject();
				jo.put("student_id", x.getStudent_id());
				jo.put("status", x.getStatus());
				ja.add(jo);
			});
			return ja.toJSONString();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
		
	}
	@PostMapping("/get")
	public String get(HttpServletRequest req) {
		try {
			int student_id = Integer.parseInt(req.getParameter("sid"));
			ArrayList<Status> arStatus = _ds.getDrillStatus4Stduent(student_id);
			System.out.println("arStatus size ["+arStatus.size()+"]");
			JSONArray ja = new JSONArray();
			arStatus.forEach(x->{
				JSONObject jo = new JSONObject();
				jo.put("drill_id", x.getDrill_id());
				jo.put("status", x.getStatus());
				ja.add(jo);
			});
			return ja.toJSONString();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
}
