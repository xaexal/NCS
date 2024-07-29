package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.etoile.app.DTO.Drill;

@Mapper
public interface _Drill {
	ArrayList<Drill> drillAll(int cid);
	ArrayList<Drill> drillAdded(int cid);
	int insertDrill(String name, String comment, int dtype_id);
	int updateDrill(String name, String comment, int dtype_id, int did);
	int deleteDrill(int did);
	Drill getDrill(int did);
	
}
