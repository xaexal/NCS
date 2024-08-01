package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.etoile.app.DTO.Course;

@Mapper
public interface _Course {
	ArrayList<Course> list();
	Course get(int cid);
	ArrayList<Course> unenrolled(int member_id);
	ArrayList<Course> applied(int member_id);
	ArrayList<Course> present(int member_id);
	ArrayList<Course> complete(int member_id);
	int insert(String title, String period1, String period2, int seat_cnt, int col_cnt, String alive, String orgname);
	int update(String title, String period1, String period2, int seat_cnt, int col_cnt, String alive, String orgname, int cid);
	int delete(int cid);
	int update2Present(int sid,String status);
}
