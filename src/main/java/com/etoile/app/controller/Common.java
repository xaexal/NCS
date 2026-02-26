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
		return result.toJSONString();
	}
}
