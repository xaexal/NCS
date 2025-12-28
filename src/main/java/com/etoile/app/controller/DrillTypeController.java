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

import com.etoile.app.DAO._Drilltype;
import com.etoile.app.DAO._Exercise;
import com.etoile.app.DTO.Drilltype;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/drilltype")
public class DrillTypeController {
	@Autowired _Drilltype _dt;
	@Autowired _Exercise _exr;

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
	public String doGet(@RequestBody Map<String,String> req) {
		try {
			int dtid = Integer.parseInt(req.get("dtid"));
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
	public String doAdd(@RequestBody Map<String,String> req,HttpSession s) {
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		int result=-1;
		try {
			String dtid = req.get("dtid");
			if(dtid==null || dtid.equals("")) {
				result = _dt.insert(req.get("typename"));
			} else {
				result = _dt.update(req.get("typename"), Integer.parseInt(dtid));
			}
			return "ok";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "fail";
	}
	@DeleteMapping("/{dtid}")
	public String doDelete(@PathVariable("dtid") int dtid) {
		int result=-1;
		try {
			result = _exr.count(dtid);
			if(result>0) return "used";
			result = _dt.delete(dtid);
			return "ok";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "fail";
	}
}