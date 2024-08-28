package com.etoile.app.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoile.app.Entity.Course;
import com.etoile.app.Repository._Course;

@Service
public class CourseSvc {
	@Autowired _Course _crs;
	
	public List<Course> listAll(){
		return _crs.findAll();
	}
	public List<Course> listAllAfterToday(){
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		return _crs.findByPeriod1GreaterThanEqual(now.format(fmt));
	}
	public Optional<Course> get(int cid) {
		return _crs.findById(cid);
	}
	public void save(int cid,String title,String period1,String period2,int seat_cnt,int col_cnt,String alive,String orgname) {
		Optional<Course> courseOpt = _crs.findById(cid);
		Course one = null;
		if(courseOpt.isPresent()) {
			one = courseOpt.get();
		} else {
			one = new Course();
		}
		one.setTitle(title);
		one.setOrgname(orgname);
		one.setPeriod1(LocalDate.parse(period1));
		one.setPeriod2(LocalDate.parse(period2));
		one.setSeatCnt(seat_cnt);
		one.setColCnt(col_cnt);
		one.setAlive(alive);
		one.setOrgname(orgname);
		_crs.save(one);
	}
	public void delete(int cid) {
		_crs.deleteById(cid);
	}
}
