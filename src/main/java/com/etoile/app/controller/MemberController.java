package com.etoile.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Member;
import com.etoile.app.DAO._Student;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {
	@Autowired _Member _mem;
	@Autowired _Student _std;

	@PostMapping("/insert")
	public String doInsert(HttpServletRequest req,Model model) {
		int result=0;
		try {
			String mobile = req.getParameter("mobile");
			String passcode = req.getParameter("passcode");
			result = _mem.insert(mobile, passcode);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}
	@PostMapping("/updateBySelf")
	public String doUpdateBySelf(HttpServletRequest req, HttpSession s) {
		System.out.println("updateBySelf");
		Map<String, String[]> parameterMap =req.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
	        String paramName = entry.getKey();
	        String[] paramValues = entry.getValue();
	        // 요청 매개변수 값에 대해 원하는 작업을 수행합니다.
	        for (String paramValue : paramValues) {
	            System.out.println(paramName+" ["+ paramValue+"]");
	        }
	    }
		System.out.println("/updateBySelf");
		int result=0;
		try {
			String mobile = req.getParameter("mobile");
			String name = req.getParameter("name");
			String passcode = req.getParameter("passcode");
			String gender = req.getParameter("gender");
			String birthday = req.getParameter("birthday");
//			String school = req.getParameter("school");
			String email = req.getParameter("email");
			String address = req.getParameter("address");
			String member_id = req.getParameter("member_id");

			int mid = Integer.parseInt(member_id);
			System.out.println("mid ["+mid+"]");
			result = _mem.updateBySelf(mobile, name, passcode, gender, birthday, email, address, mid);
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return ""+result;
	}
	@PostMapping("/updateByAdmin")
	public String doUpdateByAdmin(HttpServletRequest req, HttpSession s, Model model) {
		int result=0;
		try {
			String mobile = req.getParameter("mobile");
			String name = req.getParameter("name");
			String gender = req.getParameter("gender");
			String birthday = req.getParameter("birthday");
			String school = req.getParameter("school");
			String email = req.getParameter("email");
			String address = req.getParameter("address");
			String member_id = req.getParameter("member_id");
			String status = req.getParameter("status");
			String seq = req.getParameter("seq");

			int mid = Integer.parseInt(member_id);
			result = _mem.updateByAdmin(mobile, name, gender, birthday, school, email, address, mid);
			result = _std.updateByAdmin(Integer.parseInt(seq),req.getParameter("status"),
					Integer.parseInt(req.getParameter("sid")));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}

}
