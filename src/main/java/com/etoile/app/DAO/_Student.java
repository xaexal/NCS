package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.etoile.app.DTO.Student;

@Mapper
public interface _Student {
	ArrayList<Student> list(@Param("cid") int cid);
	ArrayList<Student> applied(@Param("cid") int cid);
	ArrayList<Student> studying(@Param("cid") int cid);
	ArrayList<Student> complete(@Param("cid") int cid);
	Student get(@Param("sid") int sid);
	int insert(@Param("mid") int member_id, @Param("cid") int course_id);
	int delete(@Param("sid") int sid);
	int update(@Param("mid") int member_id, @Param("cid") int course_id,@Param("sid") int sid);
	int updateByAdmin(@Param("seq") int seq, @Param("status") String status, @Param("sid") int sid);
	int getSID(@Param("mid") int mid, @Param("cid") int course_id);
}