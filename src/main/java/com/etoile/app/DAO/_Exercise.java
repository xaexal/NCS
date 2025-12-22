package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.etoile.app.DTO.Exercise;

@Mapper
public interface _Exercise {
	ArrayList<Exercise> list(@Param("cid") int cid); // getDrillList였음
	Exercise get(@Param("eid") int eid);
	String lastCreated(@Param("cid") int cid);
	int insert(@Param("cid") int cid, @Param("did") int did);
	int update(@Param("cid") int cid, @Param("did") int did,@Param("eid") int eid);
	int delete(@Param("eid") int eid);

}