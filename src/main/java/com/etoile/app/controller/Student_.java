package com.etoile.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Student;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/student")
public class Student_ {
	@Autowired _Student _std;

	@GetMapping("/getSID/{cid}")
	public ResponseEntity<?> getSID( @PathVariable("cid") int cid, HttpSession s) {
		try {
			Integer memberId = (Integer) s.getAttribute("member_id");
	        if (memberId == null) {
	            return ResponseEntity
	                    .status(HttpStatus.UNAUTHORIZED)
	                    .body("login");
	        }

	        // 2️⃣ SID 조회
	        int sid = _std.getSID(memberId, cid);

	        // 3️⃣ 데이터 없음
	        if (sid <= 0) {
	            return ResponseEntity
	                    .status(HttpStatus.NOT_FOUND)
	                    .body("not_found");
	        }

	        // 4️⃣ 성공
	        return ResponseEntity.ok(sid);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("error");
	    }
	}
}
