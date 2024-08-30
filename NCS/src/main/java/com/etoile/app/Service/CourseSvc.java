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
		try {
			return _crs.findAllByOrderByPeriod2Desc();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public List<Course> listApplicable(int mid){
		try {
			List<Course> arCourse=_crs.findAvailable(mid);
			return arCourse;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public List<Course> listByStatus(int mid, String status){
		List<Course> arCourse=null;
		try {
			if(status.equals("신청") || status.equals("수료") ||
			   status.equals("수강중")) {
				arCourse=_crs.findByStatus(mid,status);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return arCourse;
	}
	public Course get(int cid) {
		try {
			Course arCourse=_crs.findById(cid).orElseThrow(()->new Exception("해당하는 과정을 찾을 수 없습니다"));
			return arCourse;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public String insert(String title,String period1,String period2,int seat_cnt,int col_cnt,String alive,String orgname) {
		try {
			Course one = new Course();
			one.setTitle(title);
			one.setOrgname(orgname);
			one.setPeriod1(LocalDate.parse(period1));
			one.setPeriod2(LocalDate.parse(period2));
			one.setSeatCnt(seat_cnt);
			one.setColCnt(col_cnt);
			one.setAlive(alive);
			one.setOrgname(orgname);
			_crs.save(one);
			return "0";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "-1";
	}
	public String update(String title,String period1,String period2,int seat_cnt,int col_cnt,String alive,String orgname,int cid) {
		try {
			Course one=_crs.findById(cid).orElseThrow(()->new Exception("cid not found"));
			one.setTitle(title);
			one.setOrgname(orgname);
			one.setPeriod1(LocalDate.parse(period1));
			one.setPeriod2(LocalDate.parse(period2));
			one.setSeatCnt(seat_cnt);
			one.setColCnt(col_cnt);
			one.setAlive(alive);
			one.setOrgname(orgname);
			_crs.save(one);
			return "0";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "-1";
	}
	public String delete(int cid) {
		try {
			_crs.deleteById(cid);
			return "0";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "-1";
	}
}
