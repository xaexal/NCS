package com.etoile.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoile.app.Repository._Student;

@Service
public class StudentSvc {
	@Autowired _Student _std;
	
	public long countAsStudent(int mid) {
		return _std.countByMid(mid);
	}
}
