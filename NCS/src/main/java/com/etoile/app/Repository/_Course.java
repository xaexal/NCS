package com.etoile.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.etoile.app.Entity.Course;


public interface _Course extends JpaRepository<Course, Integer> {
	List<Course> findAllByOrderByPeriod2Desc();
	List<Course> findByPeriod1GreaterThanEqual(String today);
	
	@Query("SELECT c FROM Course c WHERE c.cid NOT IN " +
           "(SELECT s.cid FROM Student s WHERE s.memberId = :memberId) " +
			   "AND c.period1 >= CURRENT_DATE")
    List<Course> findAvailable(@Param("memberId") int memberId);
	
	@Query("SELECT c FROM Course c WHERE c.cid NOT IN " +
	           "(SELECT s.cid FROM Student s WHERE s.memberId = :memberId) " +
				   "AND s.status=:status")
	List<Course> findByStatus(@Param("memberId") int memberId,@Param("status") String status);
}
