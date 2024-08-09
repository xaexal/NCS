package com.etoile.app.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.etoile.app.DTO.Member;

@Mapper
public interface _Member {
	Member checkUser(@Param("mobile") String mobile, @Param("passcode") String password);
	int saveLoginTime(@Param("mobile") String mobile);
	int checkStudent(@Param("member_id") int mid);
	int changePasscode(@Param("member_id") String x, @Param("passcode") String y, @Param("passcode_new") String z);
}