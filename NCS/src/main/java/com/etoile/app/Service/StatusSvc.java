package com.etoile.app.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoile.app.Entity.Exercise;
import com.etoile.app.Entity.Member;
import com.etoile.app.Entity.Status;
import com.etoile.app.Entity.Student;
import com.etoile.app.Repository._Exercise;
import com.etoile.app.Repository._Status;
import com.etoile.app.Repository._Student;

@Service
public class StatusSvc {
	@Autowired _Status _status;
	@Autowired _Student _std;
	@Autowired _Exercise _exr;
	
	public List<Status> list(int eid){
		try {
			List<Status> arStatus=_status.findByExerciseId(eid);
			return arStatus;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public List<Status> list4Student(int sid){
		try {
			List<Status> arStatus=_status.findByStudentId(sid);
			return arStatus;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public String get(int sid, int eid) {
		try {
			Status status=_status.findByStudentIdAndExerciseId(sid, eid);
			return status.getStatus();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
	public int countStatus(int sid, int eid) {
		try {
			int cnt = _status.countByStudentIdAndExerciseId(sid, eid);
			return cnt;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return -1;
	}
	public boolean insert(int sid, int eid, String status) {
		try {
			Status one = new Status();
			Exercise exr = _exr.findById(eid).orElseThrow(()->new Exception("eid not found"));
			Student std = _std.findById(sid).orElseThrow(()->new Exception("sid not found"));
			one.setExercise(exr);
			one.setStudent(std);
			one.setStatus(status);
			_status.save(one);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public boolean update(int sid, int eid, String status) {
		try {
			Status one = _status.findByStudentIdAndExerciseId(sid, eid);
			if(one==null) throw new Exception("status not found");
			
			Exercise exr = _exr.findById(eid).orElseThrow(()->new Exception("eid not found"));
			Student std = _std.findById(sid).orElseThrow(()->new Exception("sid not found"));
			one.setExercise(exr);
			one.setStudent(std);
			one.setStatus(status);
			one.setUpdated(LocalDateTime.now());
			_status.save(one);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public String lastUpdated(int sid) {
		try {
			LocalDateTime time=_status.findMaxUpdatedByStudentId(sid);
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			return time.format(fmt);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
	public String recent() {
		try {
			LocalDateTime time=_status.findMaxUpdated();
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			return time.format(fmt);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
}
