package com.etoile.app.controller;


import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class Common {
	@PostMapping("/common/countHoliday")
	public String countHoliday(HttpServletRequest req) {
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
//			Map<String, String[]> parameterMap =req.getParameterMap();
//			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
//		        String paramName = entry.getKey();
//		        String[] paramValues = entry.getValue();
//		        // 요청 매개변수 값에 대해 원하는 작업을 수행합니다.
//		        for (String paramValue : paramValues) {
//		            System.out.println(paramName+" ["+ paramValue+"]");
//		        }
//		    }
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
