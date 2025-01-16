package com.etoile.app.DTO;

import lombok.Data;

@Data
public class Notice {
	int id;
	int level;
	int parent_id;
	String title;
	String content;
	String mobile;
	String created;
	String updated;
	int hit;
	int member_id; // writer's id in o_member
	String name; // writer
}
