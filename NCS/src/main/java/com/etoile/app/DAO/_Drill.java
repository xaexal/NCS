package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.etoile.app.DTO.Drill;

@Mapper
public interface _Drill {
	ArrayList<Drill> list(int cid);
	Drill get(int did);
	ArrayList<Drill> drillAdded(int cid);
	int insert(String name, String comment, int dtype_id);
	int update(String name, String comment, int dtype_id, int did);
	int delete(int did);
}
