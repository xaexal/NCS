package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.etoile.app.DTO.Drilltype;

@Mapper
public interface _Drilltype {
	ArrayList<Drilltype> list();
	Drilltype get(@Param("dtid") int id);
	int insert(@Param("typename") String typename);
	int delete(@Param("dtid") int id);
	int update(@Param("typename") String typename,@Param("dtid") int id);
}