package com.etoile.app.DTO;

import lombok.Data;

@Data
public class Course {
	int cid;
	String title;
	String orgname;
	String period1;
	String period2;
	int days;
	String endtime;
	String alive;
	int seat_cnt;
	int col_cnt;
	String created;
	String updated;
}