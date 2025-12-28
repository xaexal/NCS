package com.etoile.app.DTO;

import lombok.Data;

@Data
public class Status {
	int dsid;
	int dtype_id;
	String name;
	String comment;
	String status;
	int student_id;
	int eid;
	String created;
	String updated;
}