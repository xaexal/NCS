package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.etoile.app.DTO.Course;

@Mapper
public interface _Course {
	ArrayList<Course> courseListAll();
	ArrayList<Course> courseListUnenrolled(int member_id);
	ArrayList<Course> courseListApplied(int member_id);
	ArrayList<Course> courseListPresent(int member_id);
	ArrayList<Course> courseListComplete(int member_id);
	int insertCourse(String title, String period1, String period2, int seat_cnt, int col_cnt, String alive, String orgname);
	int updateCourse(String title, String period1, String period2, int seat_cnt, int col_cnt, String alive, String orgname, int cid);
	int deleteCourse(int cid);
}
