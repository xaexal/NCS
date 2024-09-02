package com.etoile.app.DTO;

import lombok.Data;

@Data
public class MemberDTO {
	String mobile;
	String name;
	String passcode;
	String gender;
	String birthday;
	String school;
	String email;
	String address;
	int member_id;
	String status;
	int seq;
}
