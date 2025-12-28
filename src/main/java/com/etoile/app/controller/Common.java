package com.etoile.app.controller;


import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Common {
	@PostMapping("/common/countHoliday")
	public String countHoliday(@RequestBody Map<String,String> req) {
		JSONObject result = new JSONObject();
		result.put("errcode",Errata.error);
		result.put("data", null);
//		try {
//			HttpSession s = req.getSession();
//			if (s.getAttribute("memberID") == null) {
//				result.put("errcode", Errata.signin);
//				result.put("data", "/signin");
//				throw new Exception(Message.signIn);
//			}
//			req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
//			String id=req.getParameter("id");
//			if(id==null || id.equals("")) throw new Exception("primary key ungiven");
//			int n = adao.delete(Integer.parseInt(id));
//			result.put("errcode", Errata.none);
//		} catch(Exception e) {
//			result.put("message", e.getMessage());
//		}
		return result.toJSONString();
	}
}
