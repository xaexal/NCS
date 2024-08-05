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
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/status")
public class StatusController {
	@Autowired _Status _ds;
	
	@PostMapping("/list")
	public String doList(HttpServletRequest req) {
		try {
			int drill_id = Integer.parseInt(req.getParameter("drill_id"));
			int cid = Integer.parseInt(req.getParameter("cid"));
			System.out.println("drill_id ["+drill_id+"] cid ["+cid+"]");
			
			ArrayList<Status> arStatus = _ds.list(drill_id,cid);
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
			ArrayList<Status> arStatus = _ds.get(student_id);
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
	@PostMapping("/update")
	public String doUpdate(HttpServletRequest req,HttpSession s) {
		try {
			int sid = Integer.parseInt(req.getParameter("sid"));
			int did = Integer.parseInt(req.getParameter("did"));
			String status="확인중";
			int n = _ds.count(did,sid);
			if(n==0) {
				if(s.getAttribute("level").equals("0")) {
					status="완료";
				}
				_ds.insert(did, sid, status);
			} else {
				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
	}
}