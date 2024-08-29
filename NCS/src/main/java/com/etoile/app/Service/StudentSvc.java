package com.etoile.app.Service;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoile.app.Entity.Course;
import com.etoile.app.Entity.Student;
import com.etoile.app.Repository._Course;
import com.etoile.app.Repository._Member;
import com.etoile.app.Repository._Student;

@Service
public class StudentSvc {
	@Autowired _Student _std;
	@Autowired _Member _mem;
	@Autowired _Course _crs;
	
	public List<Student> list(int cid){
		return _std.findByCourseId(cid);
	}
	public long countAsStudent(int mid) {
		return _std.countByMid(mid);
	}
	public Optional<Student> get(int sid) {
		return _std.findById(sid);
	}
	public boolean save(int mid, int cid) {
		try {
			Member member=(Member) _mem.findById(mid).orElseThrow(() -> new Exception("memberID를 찾을 수 없습니다"));
			
			Course course=_crs.findById(cid).orElseThrow(() -> new Exception("courseID를 찾을 수 없습니다"));
			
			Optional<Student> studentOpt=_std.findByMemberIdAndCourseId(mid,cid);
			Student student=null;
			if(studentOpt.isPresent()) {
				student = studentOpt.get();
			} else {
				student = new Student();
			}
			student.setMember((com.etoile.app.Entity.Member) member);
			student.setCourse(course);
			student.setStatus("신청");
			_std.save(student);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public boolean delete(int mid, int cid) {
		try {
			Student student = _std.findByMemberIdAndCourseId(mid, cid).orElseThrow(() -> new Exception("해당 memberId와 courseId로 Student를 찾을 수 없습니다."));

	        _std.delete(student);
	        return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public int getSID(int mid, int cid) {
		try {
			Student student = _std.findByMemberIdAndCourseId(mid, cid).orElseThrow(() -> new Exception("해당 memberId와 courseId로 Student를 찾을 수 없습니다."));
			return student.getSid();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return -1;
	}
}
