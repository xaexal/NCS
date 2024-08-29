package com.etoile.app.Repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.etoile.app.Entity.Exercise;

public interface _Exercise extends JpaRepository<Exercise, Integer> {
}
