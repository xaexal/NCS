package com.etoile.app.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoile.app.Entity.Course;
import com.etoile.app.Entity.Drill;
import com.etoile.app.Entity.Exercise;
import com.etoile.app.Repository._Course;
import com.etoile.app.Repository._Drill;
import com.etoile.app.Repository._Exercise;

@Service
public class ExerciseSvc {
	@Autowired _Exercise _exr;
	@Autowired _Course _crs;
	@Autowired _Drill _drl;
	
	public List<Object[]> list(int cid){
		try {
			List<Object[]> list=_exr.list(cid);
			return list;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public Exercise get(int eid) {
		try {
			Exercise one=_exr.findById(eid).orElseThrow(()->new Exception("eid not found"));
			return one;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public String lastCreated(int cid) {
		try {
			LocalDateTime last = _exr.findMaxCreatedByCourseId(cid);
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
			return last.format(fmt);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
	public boolean insert(int cid, int did) {
		try {
			Course crs=_crs.findById(cid).orElseThrow(()->new Exception("cid not found"));
			Drill drl=_drl.findById(did).orElseThrow(()->new Exception("did not found"));
			Exercise exr= new Exercise();
			exr.setCourse(crs);
			exr.setDrill(drl);
			_exr.save(exr);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public boolean update(int cid,int did, int eid) {
		try {
			Exercise exr = _exr.findById(eid).orElseThrow(()->new Exception("No existing eid"));
			Course crs=_crs.findById(cid).orElseThrow(()->new Exception("cid not found"));
			Drill drl=_drl.findById(did).orElseThrow(()->new Exception("did not found"));
			exr.setCourse(crs);
			exr.setDrill(drl);
			_exr.save(exr);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public boolean delete(int eid) {
		try {
			_exr.deleteById(eid);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
