package com.etoile.app.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoile.app.Entity.Course;
import com.etoile.app.Repository._Course;

@Service
public class CourseSvc {
	@Autowired _Course _crs;
	
	public ArrayList<Course> listAll(){
		return (ArrayList<Course>) _crs.findAll();
	}
}
