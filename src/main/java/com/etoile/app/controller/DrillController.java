package com.etoile.app.controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Drill;
import com.etoile.app.DTO.Drill;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/drill")
public class DrillController {
	@Autowired _Drill _drl;
	
	@PostMapping("/list")
	public String doList(HttpServletRequest req) {
		try {
			int cid = Integer.parseInt(req.getParameter("cid"));
			
			ArrayList<Drill> alDrill = _drl.list(cid);
			System.out.println("Drill size="+alDrill.size());
			
			JSONArray ja = new JSONArray();
			alDrill.forEach(x->{
	    		JSONObject jo = new JSONObject();
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
	@PostMapping("/get")
	public String get(HttpServletRequest req) {
		try {
			int did = Integer.parseInt(req.getParameter("did"));
			Drill x = _drl.get(did);
			JSONObject jo = new JSONObject();
    		jo.put("did", x.getDid());
    		jo.put("dtype_id", x.getDtype_id());
    		jo.put("name", x.getName());
    		jo.put("comment", x.getComment());
    		jo.put("created", x.getCreated());
    		jo.put("updated", x.getUpdated());
    		return jo.toJSONString();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
	@PostMapping("/delete")
	public String delete(HttpServletRequest req) {
		try {
			int did = Integer.parseInt(req.getParameter("did"));
			int n = _drl.delete(did);
			return ""+n;
		} catch(Exception e) {
			return "";
		}
	}
	@PostMapping("/add")
	public String add(HttpServletRequest req) {
		String did = req.getParameter("did");
		int n=0;
		if(did==null || did.equals("")) {
			n = _drl.insert(req.getParameter("name"),req.getParameter("comment"),
						Integer.parseInt(req.getParameter("type_id")));
		} else {
			n = _drl.update(req.getParameter("name"),req.getParameter("comment"),
						Integer.parseInt(req.getParameter("type_id")),
						Integer.parseInt(did));
		}
		return ""+n;
	}
}