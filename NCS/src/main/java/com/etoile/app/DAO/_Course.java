package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.etoile.app.DTO.Course;

@Mapper
public interface _Course {
	ArrayList<Course> courseAll();
	ArrayList<Course> courseUnenrolled(int member_id);
	ArrayList<Course> courseApplied(int member_id);
	ArrayList<Course> coursePresent(int member_id);
	ArrayList<Course> courseComplete(int member_id);
	int insertCourse(String title, String period1, String period2, int seat_cnt, int col_cnt, String alive, String orgname);
	int updateCourse(String title, String period1, String period2, int seat_cnt, int col_cnt, String alive, String orgname, int cid);
	int deleteCourse(int cid);
	Course getCourse(int cid);
	int update2Present(int sid,String status);
}
