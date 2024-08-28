package com.etoile.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etoile.app.Entity.Course;


public interface _Course extends JpaRepository<Course, Integer> {
	List<Course> findByPeriod1GreaterThanEqual(String today);
}
