package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.etoile.app.DTO.Drill;

@Mapper
public interface _Drill {
	ArrayList<Drill> list(@Param("cid") int cid);
	Drill get(int did);
	int insert(@Param("name") String name, @Param("comment") String comment, @Param("dtype_id") int dtype_id);
	int update(@Param("name") String name, @Param("comment") String comment, @Param("dtype_id")  int dtype_id, @Param("did")  int did);
	int delete(@Param("did") int did);
}