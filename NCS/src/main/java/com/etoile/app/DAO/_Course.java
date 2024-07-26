package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.etoile.app.DTO.Course;

@Mapper
public interface _Course {
	ArrayList<Course> courseListAll();
}
