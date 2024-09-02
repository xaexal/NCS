package com.etoile.app.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.etoile.app.Entity.Exercise;

public interface _Exercise extends JpaRepository<Exercise, Integer> {

	@Query("SELECT a.eid,b.did,b.name,b.comment,b.dtype_id,b.created,b.updated "+
			"FROM Exercise a, Drill b WHERE a.course_id= :cid AND a.drill_id=b.did "+
			"ORDER BY a.created")
	List<Object[]> list(@Param("cid") int cid);
	
	LocalDateTime findMaxCreatedByCourseId(int cid);
}
