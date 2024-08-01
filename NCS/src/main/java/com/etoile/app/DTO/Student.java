package com.etoile.app.DTO;

import lombok.Data;

@Data
public class Student {
	int sid;
	int seq;
	String active;
	int course_id;
	int member_id;
	String status;
	String created;
	String updated;
}