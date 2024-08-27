package com.etoile.app.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoile.app.Entity.Member;
import com.etoile.app.Repository._Member;

@Service
public class MemberSvc {
	@Autowired _Member _mem;

	public Member checkUser(String mobile, String passcode) {
		return _mem.findMemberByMobileAndPasscode(mobile, passcode);
	}
	public void save(String mobile, String passcode) {
		Member member = new Member();
		member.setMobile(mobile);
		member.setPasscode(passcode);
		_mem.save(member);
	}
	public void saveLoginTime(String mobile) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		Member member = _mem.findMemberByMobile(mobile);
		member.setLoginTm(now.format(fmt));
		_mem.save(member);
	}
}
