package com.etoile.app.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoile.app.Entity.Member;
import com.etoile.app.Repository._Member;

@Service
public class MemberSvc {
	@Autowired _Member _mem;

	public Member checkUser(String mobile, String passcode) {
		try {
			Member member=_mem.findByMobile(mobile);
			if(member==null) throw new Exception("등록되지 않은 모바일번호입니다");
			if(member.getPasscode().equals(passcode)) return member;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	int checkStudent(int mid) {
		return _mem.countByIdAndStatus(mid,"수강중");
	}
	public boolean save(String mobile, String passcode) {
		try {
			Member member=_mem.findByMobile(mobile);
			if(member==null) {
				member = new Member();
			}
			member.setMobile(mobile);
			member.setPasscode(passcode);
			_mem.save(member);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public boolean saveLoginTime(String mobile) {
		try {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			Member member = _mem.findByMobile(mobile);
			member.setLoginTm(now.format(fmt));
			_mem.save(member);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public boolean changePasscode(int mid, String oldPasscode, String newPasscode) {
		try {
			Member member=_mem.findById(mid).orElseThrow(()->new Exception("Member not found"));
			if(!member.getPasscode().equals(oldPasscode)) 
				throw new Exception("현재 비밀번호가 맞지 않습니다");
			member.setPasscode(newPasscode);
			_mem.save(member);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public Member get(String mobile){
		try {
			Member member=_mem.findByMobile(mobile);
			return member;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null; 
	}
	public boolean updateByAdmin(String mobile,String name,String gender,String birthday,
			String school,String email,String address,int mid) {
		try {
			Member member = _mem.findById(mid).orElseThrow(()->new Exception("Nobody by member id"));
			member.setMobile(mobile);
			member.setName(name);
			member.setGender(gender);
			member.setBirthday(birthday);
			member.setSchool(school);
			member.setEmail(email);
			member.setAddress(address);
			LocalDateTime now = LocalDateTime.now();
			member.setUpdated(now);
			_mem.save(member);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());		
		}
		return false;
		
	}
}
