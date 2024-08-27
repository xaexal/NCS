package com.etoile.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etoile.app.Entity.Student;

public interface _Student extends JpaRepository<Student, Integer> {
	long countByMid(int mid);
}
