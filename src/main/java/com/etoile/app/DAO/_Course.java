package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.etoile.app.DTO.Course;

@Mapper
public interface _Course {
	ArrayList<Course> listAll();
	ArrayList<Course> student(@Param("mid") int a);
	ArrayList<Course> applicable(@Param("mid") int member_id);
	Course get(@Param("cid") int cid);
	ArrayList<Course> enrolled(@Param("mid") int member_id);
	ArrayList<Course> applied(@Param("mid") int member_id);
	ArrayList<Course> completed(@Param("mid") int member_id);
	int insert(@Param("title") String title, @Param("period1") String period1, @Param("period2") String period2,
			   @Param("seat_cnt") int seat_cnt, @Param("col_cnt") int col_cnt, @Param("alive") String alive,
			   @Param("orgname") String orgname);
	int update(@Param("title") String title, @Param("period1") String period1, @Param("period2") String period2,
			   @Param("seat_cnt") int seat_cnt, @Param("col_cnt") int col_cnt, @Param("alive") String alive,
			   @Param("orgname") String orgname, @Param("cid") int cid);
	int delete(@Param("cid") int cid);
}