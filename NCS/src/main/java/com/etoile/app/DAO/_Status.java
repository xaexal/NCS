package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.etoile.app.DTO.Status;

@Mapper
public interface _Status {
	ArrayList<Status> list(@Param("drill_id") int drill_id,@Param("cid") int cid) ;
	String get(@Param("drill_id") int drill_id,@Param("sid") int sid);
	int count(@Param("drill_id") int drill_id,@Param("sid") int sid);
	int insert(@Param("drill_id") int drill_id, @Param("sid") int sid,@Param("status") String status);
	int update(@Param("drill_id") int drill_id, @Param("sid") int sid,@Param("status") String status);
}