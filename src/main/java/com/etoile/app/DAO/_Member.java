package com.etoile.app.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.etoile.app.DTO.Member;

@Mapper
public interface _Member {
	Member checkUser(@Param("mobile") String mobile, @Param("passcode") String password);
	int saveLoginTime(@Param("id") int id);
	int checkStudent(@Param("member_id") int mid);
	int changePasscode(@Param("mobile") String x, @Param("passcode") String y, @Param("passcode1") String z);
	int setTempPasscode(@Param("mobile") String x, @Param("passcode_new") String y);
	Member get(@Param("mobile") String mobile);
	Member getPersonal(@Param("id") int id);
	int insert(@Param("mobile") String mobile, @Param("passcode") String passcode);
	int updateBySelf(@Param("mobile") String mobile, @Param("name") String name, @Param("passcode") String passcode,
			@Param("gender") String gender, @Param("birthday") String birthday,
			@Param("email") String email, @Param("address") String address,
			@Param("mid") int mid);
	int updateByAdmin(@Param("mobile") String mobile, @Param("name") String name,
			@Param("gender") String gender, @Param("birthday") String birthday, @Param("school") String school,
			@Param("email") String email, @Param("address") String address,
			@Param("mid") int mid);
	int delete(@Param("mid") int mid);
	int logout(@Param("mobile") String mobile);
}