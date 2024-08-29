package com.etoile.app.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.etoile.app.Entity.Status;

public interface _Status extends JpaRepository<Status, Integer> {
	List<Status> findByExerciseId(int eid);
	List<Status> findByStudentId(int sid);
	Status findByStudentIdAndExerciseId(int sid, int eid);
	int countByStudentIdAndExerciseId(int sid, int eid);
	
	@Query("SELECT MAX(s.updated) FROM Status s WHERE s.student.id = :sid")
    LocalDateTime findMaxUpdatedByStudentId(@Param("sid") int sid);
	
	@Query("SELECT MAX(s.updated) FROM Status")
	LocalDateTime findMaxUpdated();
}
