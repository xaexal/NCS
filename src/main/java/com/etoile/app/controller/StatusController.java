package com.etoile.app.controller;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Status;
import com.etoile.app.DTO.Status;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/status")
public class StatusController {
	@Autowired _Status _ds;

	@GetMapping("/list/{eid}")
	public String doList(@PathVariable("eid") int eid) {
		try {
			System.out.println("eid ["+eid+"]");

			ArrayList<Status> arStatus = _ds.list(eid);
			System.out.println("arStatus size ["+arStatus.size()+"]");
			JSONArray ja = new JSONArray();
			arStatus.forEach(x->{
				JSONObject jo = new JSONObject();
				jo.put("sid", x.getStudent_id());
				jo.put("dsid",x.getDsid());
				jo.put("status", x.getStatus());
				ja.add(jo);
			});
			return ja.toJSONString();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "";
		}

	}
	@GetMapping("/list4student/{sid}")
	public String doList4student(@PathVariable("sid") int sid) {
		try {
			System.out.println("sid ["+sid+"]");

			ArrayList<Status> arStatus = _ds.list4Student(sid);
			System.out.println("arStatus size ["+arStatus.size()+"]");
			JSONArray ja = new JSONArray();
			arStatus.forEach(x->{
				JSONObject jo = new JSONObject();
				jo.put("dsid", x.getDsid());
				jo.put("drill_id", x.getDrill_id());
				jo.put("status", x.getStatus());
				jo.put("student_id", x.getStudent_id());
				jo.put("eid", x.getEid());
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
//	@PostMapping("/get")
//	public String get(@RequestBody Map<String,String> req) {
//		try {
//			int student_id = Integer.parseInt(req.get("sid"));
//			ArrayList<Status> arStatus = _ds.get(student_id);
//			System.out.println("arStatus size ["+arStatus.size()+"]");
//			JSONArray ja = new JSONArray();
//			arStatus.forEach(x->{
//				JSONObject jo = new JSONObject();
//				jo.put("drill_id", x.getDrill_id());
//				jo.put("status", x.getStatus());
//				ja.add(jo);
//			});
//			return ja.toJSONString();
//		} catch(Exception e) {
//			System.out.println(e.getMessage());
//			return "";
//		}
//	}
	@PostMapping("/")
	public String doUpdate(@RequestBody Map<String,String> req,HttpSession s) {
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		int result=-1;
		try {
			String mobile = (String)s.getAttribute("mobile");
			if(mobile==null || mobile.equals("")) return "";

			int level=-1;
			Object oLevel = s.getAttribute("level");
			if(oLevel instanceof Integer) level = (Integer) oLevel;
			else if(oLevel instanceof String) level = Integer.parseInt((String) oLevel);

			int sid = Integer.parseInt(req.get("sid"));
			int eid = Integer.parseInt(req.get("eid"));
			String dsid = req.get("dsid");

			if(dsid==null || dsid.equals("")) {
				result=_ds.insert(eid, sid, req.get("status"));
			} else {
				result=_ds.update(eid, sid, req.get("status"));
			}
			if(result==1) return "ok";
		} catch (Exception e) {
			System.out.println(e.getMessage());
//			System.out.println("error");
		}
		return "fail";
	}
	@PostMapping("/lastUpdated")
	public String lastUpdated(@RequestBody Map<String,String> req) {
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		String updated="";
		try {
			int sid = Integer.parseInt(req.get("sid"));
			updated = _ds.lastUpdated(sid);
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
//		System.out.println("updated ["+updated+"]");
		return updated;
	}

}