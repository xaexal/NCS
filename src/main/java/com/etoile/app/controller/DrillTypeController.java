package com.etoile.app.controller;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Drilltype;
import com.etoile.app.DTO.Drilltype;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/drilltype")
public class DrillTypeController {
	@Autowired _Drilltype _dt;

	@GetMapping("/list")
	public String doList() {
		try {
			ArrayList<Drilltype> arDT = _dt.list();
			System.out.println("arDT size="+arDT.size());

			JSONArray ja = new JSONArray();
			arDT.forEach(x->{
	    		JSONObject jo = new JSONObject();
	    		jo.put("dtid", x.getDtid());
	    		jo.put("typename", x.getTypename());
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
	@GetMapping("/get")
	public String doGet(HttpServletRequest req) {
		try {
			int dtid = Integer.parseInt(req.getParameter("dtid"));
			Drilltype x = _dt.get(dtid);
			JSONObject jo = new JSONObject();
			jo.put("dtid", x.getDtid());
    		jo.put("typename", x.getTypename());
    		jo.put("created", x.getCreated());
    		jo.put("updated", x.getUpdated());
    		return jo.toJSONString();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
	@PostMapping("/")
	public String doAdd(HttpServletRequest req) {
		int result=-1;
		try {
			String dtid = req.getParameter("");
			if(dtid==null || dtid.equals("")) {
				result = _dt.insert(req.getParameter("typename"));
			} else {
				result = _dt.update(req.getParameter("typename"), Integer.parseInt(dtid));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}
	@DeleteMapping("/delete")
	public String doDelete(HttpServletRequest req) {
		int result=-1;
		try {
			int dtid = Integer.parseInt(req.getParameter("dtid"));
			result = _dt.delete(dtid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}
}