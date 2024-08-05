package com.etoile.app.DAO;

import org.apache.ibatis.annotations.Mapper;

import com.etoile.app.DTO.Member;

@Mapper
public interface _Member {
	Member checkUser(String mobile, String password);
	int saveLoginTime(String mobile);
	int checkStudent(int mid);
	int changePasscode(String x, String y, String z);
}