package com.etoile.app.controller;

import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DTO.MemberDTO;
import com.etoile.app.Service.MemberSvc;
import com.etoile.app.Service.StudentSvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {
	private final MemberSvc _mem;
	private final StudentSvc _std;
	
	MemberController(){
		this._mem=new MemberSvc();
		this._std=new StudentSvc();
	}
	@PostMapping("/insert")
	public String doInsert(HttpServletRequest req,Model model) {
		boolean result=false;
		try {
			String mobile = req.getParameter("mobile");
			String passcode = req.getParameter("passcode");
			result = _mem.insert(mobile, passcode);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(result) return "0";
		else return "-1";
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
			MemberDTO mdto = new MemberDTO();
			mdto.setMobile(req.getParameter("mobile"));
			mdto.setName(req.getParameter("name"));
			String passcode = req.getParameter("passcode");
			String gender = req.getParameter("gender");
			String birthday = req.getParameter("birthday");
//			String school = req.getParameter("school");
			String email = req.getParameter("email");
			String address = req.getParameter("address");
			String member_id = req.getParameter("member_id");
		
			int mid = Integer.parseInt(member_id);
			System.out.println("mid ["+mid+"]");
			result = _mem.update(mobile, name, passcode, gender, birthday, email, address, mid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		return ""+result;
	}
	@PostMapping("/updateByAdmin")
	public String doUpdateByAdmin(HttpServletRequest req, HttpSession s, Model model) {
		int result=0;
		try {
			MemberDTO mdto = new MemberDTO();
			mdto.setMobile(req.getParameter("mobile"));
			mdto.setName(req.getParameter("name"));
			mdto.setGender(req.getParameter("gender"));
			mdto.setBirthday(req.getParameter("birthday"));
			mdto.setSchool(req.getParameter("school"));
			mdto.setEmail(req.getParameter("email"));
			mdto.setAddress(req.getParameter("address"));
			mdto.setMember_id(Integer.parseInt(req.getParameter("member_id")));
			mdto.setStatus(req.getParameter("status"));
			mdto.setSeq(Integer.parseInt(req.getParameter("seq")));
			
			int mid = mdto.getMember_id();
			result = _mem.update(mdto,Integer.parseInt(req.getParameter("sid")),"admin");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ""+result;
	}
}
