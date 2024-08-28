package com.etoile.app.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoile.app.Entity.Course;
import com.etoile.app.Repository._Course;

@Service
public class CourseSvc {
	@Autowired _Course _crs;
	
	public List<Course> listAll(){
		return _crs.findAll();
	}
	public List<Course> listAllAfterToday(){
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		return _crs.findByPeriod1GreaterThanEqual(now.format(fmt));
	}
}
