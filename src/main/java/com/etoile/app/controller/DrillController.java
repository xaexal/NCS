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

import com.etoile.app.DAO._Drill;
import com.etoile.app.DTO.Drill;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins="http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/drill")
public class DrillController {
	@Autowired _Drill _drl;

	@GetMapping("/list/{course}")
	public String doList(@PathVariable("course") int course) {
	    try {
	        System.out.println("course ["+course+"]");

			ArrayList<Drill> alDrill = _drl.list(course);
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
	@GetMapping("/get/{drill_id}")
	public String get(@PathVariable("drill_id") int did) {
		try {
			System.out.println("drill_id ["+did+"]");
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
	@DeleteMapping("/delete/{drill_id}")
	public String delete(@PathVariable("drill_id") int did) {
		try {
			System.out.println("did ["+did+"]");
			int n = _drl.delete(did);
			System.out.println("n ["+n+"]");
			return ""+n;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}
	@PostMapping("/add")
	public String add(@RequestBody Map<String,String> req) {
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		try {
			String did = req.get("did");
			int n=0;
			if(did==null || did.equals("")) {
				n = _drl.insert(req.get("name"),req.get("comment"),
							Integer.parseInt(req.get("dtype_id")));
			} else {
				n = _drl.update(req.get("name"),req.get("comment"),
							Integer.parseInt(req.get("dtype_id")),
							Integer.parseInt(did));
			}
			if(n>0) return "ok";
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "fail";
	}
}