package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.etoile.app.DTO.Status;

@Mapper
public interface _Status {
	ArrayList<Status> drillStatusAll(int drill_id,int cid) ;
	ArrayList<Status> getDrillStatus4Stduent(int sid);
	int changeStatus(int drill_id, int sid);
}
