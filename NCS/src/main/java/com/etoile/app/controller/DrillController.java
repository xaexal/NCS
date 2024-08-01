package com.etoile.app.controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Drill;
import com.etoile.app.DTO.Drill;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/drill")
public class DrillController {
	@Autowired _Drill drl;
	
	@PostMapping("/list")
	public String drillAll(HttpServletRequest req) {
		String cid = req.getParameter("cid");
		if(cid == null ||  cid.equals("")) return "";
		
		ArrayList<Drill> alDrill = drl.drillAll(Integer.parseInt(cid));
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
	}
	@PostMapping("/drillAdded")
	public String drillAdded(HttpServletRequest req) {
		try {
			int cid = Integer.parseInt(req.getParameter("cid"));
			ArrayList<Drill> alDrill = drl.drillAdded(cid);
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
	@PostMapping("/getDrill")
	public String getDrill(HttpServletRequest req) {
		try {
			int did = Integer.parseInt(req.getParameter("did"));
			Drill x = drl.getDrill(did);
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
	@PostMapping("/delDrill")
	public String deleteDrill(HttpServletRequest req) {
		try {
			int did = Integer.parseInt(req.getParameter("did"));
			int n = drl.deleteDrill(did);
			return ""+n;
		} catch(Exception e) {
			return "";
		}
	}
	@PostMapping("/addDrill")
	public String addDrill(HttpServletRequest req) {
		String did = req.getParameter("did");
		int n=0;
		if(did==null || did.equals("")) {
			n = drl.insertDrill(req.getParameter("name"),req.getParameter("comment"),
						Integer.parseInt(req.getParameter("dtype_id")));
		} else {
			n = drl.updateDrill(req.getParameter("name"),req.getParameter("comment"),
						Integer.parseInt(req.getParameter("dtype_id")),
						Integer.parseInt(did));
		}
		return ""+n;
	}
}
