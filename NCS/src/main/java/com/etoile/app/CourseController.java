package com.etoile.app;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CourseController {
	@PostMapping("/courseListAll")
	public String courseListAll(HttpServletRequest req) {
		LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        System.out.println(formattedDate);
        if(req.getParameter("member_id")!=null)
	}
	
}
