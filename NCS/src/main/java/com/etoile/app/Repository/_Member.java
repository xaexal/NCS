package com.etoile.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etoile.app.Entity.Member;

public interface _Member extends JpaRepository<Member, Integer> {
	Member findByMobile(String mobile);
	int countByIdAndStatus(int mid, String status);
}