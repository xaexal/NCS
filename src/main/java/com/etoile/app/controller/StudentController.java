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

import com.etoile.app.DAO._Member;
import com.etoile.app.DAO._Status;
import com.etoile.app.DAO._Student;
import com.etoile.app.DTO.Member;
import com.etoile.app.DTO.Student;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired _Student _std;
	@Autowired _Status _status;
	@Autowired _Member _mem;

	@GetMapping("/list/{cid}/{status}")
	public String doList(@PathVariable("cid") int cid,
						 @PathVariable("status") String status, HttpSession s) {
		try {
			ArrayList<Student> arStudent = _std.list(cid,status);
			System.out.println("arStudent size="+arStudent.size());

			JSONArray ja = new JSONArray();
			arStudent.forEach(x->{{
				JSONObject jo = new JSONObject();
				jo.put("sid", x.getSid());
				jo.put("name", x.getName());
				jo.put("gender", x.getGender());
				jo.put("mobile", x.getMobile());
				jo.put("birthday", x.getBirthday());
				jo.put("seq",x.getSeq());
				jo.put("status", x.getStatus());
				jo.put("member_id", x.getMember_id());
				jo.put("course_id", x.getCourse_id());
				ja.add(jo);
			}});
			return ja.toJSONString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
	@GetMapping("/get/{sid}")
	public String doGet(@PathVariable("sid") int sid,HttpSession s) {
		try {
			Student x = _std.get(sid);

			JSONObject jo = new JSONObject();
			jo.put("sid", x.getSid());
			jo.put("name", x.getName());
			jo.put("gender", x.getGender());
			jo.put("mobile", x.getMobile());
			jo.put("birthday", x.getBirthday());
			jo.put("school", x.getSchool());
			jo.put("address", x.getAddress());
			jo.put("seq",x.getSeq());
			jo.put("status", x.getStatus());
			jo.put("member_id", x.getMember_id());
			jo.put("course_id", x.getCourse_id());
			return jo.toJSONString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
	@PostMapping("/add")	// apply2Course()
	public String doAdd(@RequestBody Map<String,String> req, HttpSession s) {
		System.out.println("--------------------------------------");
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		System.out.println("--------------------------------------");
		int result=0;
		try {
			Member member = new Member();
			String member_id = req.get("member_id");
			if(member_id==null || member_id.equals("")) {
				String mobile = req.get("mobile");
				if(mobile==null || mobile.equals("")) throw new Exception("모바일번호는 필수사항");
				member.setMobile(mobile);
				member.setName(req.get("name"));
				member.setGender(req.get("gender"));
				member.setBirthday(req.get("birthday"));
				member.setAddress(req.get("address"));
				member.setSchool(req.get("school"));
				_mem.insert(member);
			} else {
				member.setMid(Integer.parseInt(member_id));
			}
			String sid = req.get("sid");
			if(sid==null || sid.equals("")) {
				result = _std.insert(Integer.parseInt(req.get("member_id")),
									 Integer.parseInt(req.get("course_id")));
			} else {
				result = _std.update(Integer.parseInt(req.get("member_id")),
									 Integer.parseInt(req.get("course_id")),
									 Integer.parseInt(sid));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}
	@DeleteMapping("/delete/{sid}")
	public String doDelete(@PathVariable("sid") int sid,HttpSession s) {
		int result = -1;
		try {
			int n =_status.delete(sid);
			System.out.println("n="+n);
			result = _std.delete(sid);
			System.out.println("result="+result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}
	@GetMapping("/getSID/{mid}/{cid}")
	public String getSID(@PathVariable("mid") int mid, @PathVariable("cid") int cid, HttpSession s) {
		int sid = -1;
		try {
			sid = _std.getSID(mid, cid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+sid;
	}
}