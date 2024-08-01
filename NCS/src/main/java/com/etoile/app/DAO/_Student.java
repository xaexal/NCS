package com.etoile.app.DAO;

import java.util.ArrayList;

import com.etoile.app.DTO.Student;

public interface _Student {
	ArrayList<Student> list();
	Student get();
	int insert();
	int delete();
	int update();
}
