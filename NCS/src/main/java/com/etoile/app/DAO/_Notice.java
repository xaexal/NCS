package com.etoile.app.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.etoile.app.DTO.Notice;

@Mapper
public interface _Notice {
	ArrayList<Notice> selectAll(String keyword, int start, int pagesize);
	ArrayList<Notice> selectByLevel(int level,int start,int pagesize);
	Notice selectOne(int id);
	int insert(String title,String content,int memberID,String level,int parentID);
	int update(String title,String content,int memberID,String level,int parentID,int id);
	int delete(int id);
	int addHit(int id);
	int getLast(int member_id);
}