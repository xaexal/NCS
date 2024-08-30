package com.etoile.app.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etoile.app.Entity.Student;

public interface _Student extends JpaRepository<Student, Integer> {
	List<Student> findByCourseId(int cid);
	long countByMid(int mid);
	int countByCourseId(int cid);
	Optional<Student> findByMemberIdAndCourseId(int mid, int cid);
}
