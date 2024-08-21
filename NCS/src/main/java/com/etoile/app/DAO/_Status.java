package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.etoile.app.DTO.Status;

@Mapper
public interface _Status {
	ArrayList<Status> list(@Param("eid") int eid) ;
	ArrayList<Status> list4Student(@Param("student_id") int sid);
	String lastUpdated(@Param("sid") int sid);
	String get(@Param("eid") int eid,@Param("sid") int sid);
	int count(@Param("eid") int eid,@Param("sid") int sid);
	int insert(@Param("eid") int eid, @Param("sid") int sid,@Param("status") String status);
	int update(@Param("eid") int eid, @Param("sid") int sid,@Param("status") String status);
}