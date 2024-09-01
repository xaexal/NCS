package com.etoile.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.etoile.app.Entity.Drill;

public interface _Drill extends JpaRepository<Drill, Integer> {
	@Query("SELECT c FROM Drill c WHERE c.did not in ("+
			"SELECT d.drill_id FROM Exercise d where d.course_id= :cid)"+
			"ORDER BY c.Created")
	List<Drill> list(@Param("cid") int cid);
}
