package com.etoile.app.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.Repository._Drill;
import com.etoile.app.Service.CourseSvc;
import com.etoile.app.Service.DrillSvc;
import com.etoile.app.Entity.Drill;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/drill")
public class DrillController {
	private final DrillSvc drlSvc = new DrillSvc();
	
	@PostMapping("/list")
	public String doList(HttpServletRequest req) {
		try {
			int cid = Integer.parseInt(req.getParameter("cid"));
			
			List<Drill> alDrill = drlSvc.list(cid);
			System.out.println("Drill size="+alDrill.size());
			
			JSONArray ja = new JSONArray();
			alDrill.forEach(x->{
	    		JSONObject jo = new JSONObject();
	    		jo.put("did", x.getDid());
	    		jo.put("dtype_id", x.getDrilltype());
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
			Drill x = drlSvc.get(did);
			JSONObject jo = new JSONObject();
    		jo.put("did", x.getDid());
    		jo.put("dtype_id", x.getDrilltype());
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
			if(drlSvc.delete(did)) return "0";
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "-1";
	}
	@PostMapping("/add")
	public String add(HttpServletRequest req) {
		String did = req.getParameter("did");
		boolean result=true;
		if(did==null || did.equals("")) {
			result = drlSvc.insert(req.getParameter("name"),req.getParameter("comment"),
						Integer.parseInt(req.getParameter("type_id")));
		} else {
			result = drlSvc.update(req.getParameter("name"),req.getParameter("comment"),
						Integer.parseInt(req.getParameter("type_id")),
						Integer.parseInt(did));
		}
		if(result) return "0";
		else return "-1";
	}
}