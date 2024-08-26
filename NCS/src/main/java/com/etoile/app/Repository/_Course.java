package com.etoile.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etoile.app.Entity.Course;


public interface _Course extends JpaRepository<Course, Long> {
	
}
