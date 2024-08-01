package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.etoile.app.DTO.Exercise;

@Mapper
public interface _Exercise {
	ArrayList<Exercise> drillListAll(@Param("cid") int cid); // getDrillList였음
	int addDrill2List(@Param("cid") int cid, @Param("did") int did);
	int delDrill4List(@Param("cid") int cid, @Param("did") int did);
	
}
