package com.etoile.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Member;
import com.etoile.app.DAO._Student;
import com.etoile.app.DTO.Member;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/member")
public class MemberController {
	@Autowired _Member _mem;
	@Autowired _Student _std;

	@PostMapping("/insert")
	public String doInsert(@RequestBody Map<String,String> req, HttpSession s) {
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		int result=0;
		try {
			Member member = new Member();
			member.setMobile(req.get("mobile"));
			member.setPasscode(req.get("passcode"));
			result = _mem.insert(member);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}
	@PostMapping("/updateBySelf")
	public String doUpdateBySelf(@RequestBody Map<String, Object> param, HttpSession s) {
		System.out.println("updateBySelf");
		param.forEach((k,v) -> System.out.println(k + " : " + v));
		int result=0;
		try {
			String mobile = (String)param.get("mobile");
			String name = (String)param.get("name");
			String passcode = (String)param.get("passcode");
			String gender = (String)param.get("gender");
			String birthday = (String)param.get("birthday");
//			String school = param.get("school");
			String email = (String)param.get("email");
			String address = (String)param.get("address");

			Integer mid = (Integer) s.getAttribute("member_id");
			System.out.println("mid ["+mid+"]");
	        if (mid == null) {
	            throw new RuntimeException("로그인 세션이 없습니다.");
	        }
			System.out.println("mid ["+mid+"]");
			result = _mem.updateBySelf(mobile, name, passcode, gender, birthday, email, address, mid);
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return ""+result;
	}
	@PostMapping("/updateByAdmin")
	public String doUpdateByAdmin(@RequestBody Map<String,String>  req, HttpSession s) {
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		int result=0;
		try {
			String mobile = req.get("mobile");
			String name = req.get("name");
			String gender = req.get("gender");
			String birthday = req.get("birthday");
			String school = req.get("school");
			String email = req.get("email");
			String address = req.get("address");
			String member_id = req.get("member_id");
			String status = req.get("status");
			String seq = req.get("seq");

			int mid = Integer.parseInt(member_id);
			result = _mem.updateByAdmin(mobile, name, gender, birthday, school, email, address, mid);
			result = _std.updateByAdmin(Integer.parseInt(seq),req.get("status"),
					Integer.parseInt(req.get("sid")));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}
}
