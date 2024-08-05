package com.etoile.app.DTO;

import lombok.Data;

@Data
public class Member {
	int mid;
	String mobile;
	String name;
	String passcode;
	String gender;
	String birthday;
	String school;
	int level;
	String login_tm;
	String logout_tm;
	String email;
	String absence;
	String ipaddr;
	String address;
	boolean active;
	String created;
	String updated;
}