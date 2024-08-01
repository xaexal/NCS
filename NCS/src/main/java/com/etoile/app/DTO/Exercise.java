package com.etoile.app.DTO;

import lombok.Data;

@Data
public class Exercise {
	int dlid;
	int did;
	int dtype_id;
	String name;
	String comment;
	String created;
	String updated;
}
