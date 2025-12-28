package com.etoile.app.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.DAO._Status;
import com.etoile.app.DTO.Status;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials="true")
@RestController
@RequestMapping("/status")
public class Status_ {
	@Autowired _Status _status;

	@GetMapping("/4student/{cid}/{sid}")
	public ResponseEntity<?> getStatusList4Student(@PathVariable("cid") int cid,
												   @PathVariable("sid") int sid, HttpSession s) {
		try {
			ArrayList<Status> alStatus = _status.status4student(cid,sid);
			return ResponseEntity.ok(alStatus);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
		}
	}
	@PutMapping("/update")
	public ResponseEntity<?> changeStatus(@RequestBody Map<String,String> req,HttpSession s){
		req.forEach((k, v) -> System.out.println(k + " [" + v+"]"));
		try {
			int result = 0;
			String dsid = req.get("dsid");
			if(dsid==null || dsid.equals("")) {
				result = _status.insert(Integer.parseInt(req.get("eid")),
							   			Integer.parseInt(req.get("sid")), req.get("status"));
			} else {
				result = _status.update(Integer.parseInt(req.get("dsid")), req.get("status"));
			}
			return ResponseEntity.ok(result);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
		}
	}

}
