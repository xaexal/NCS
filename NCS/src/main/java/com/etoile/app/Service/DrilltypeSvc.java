package com.etoile.app.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.etoile.app.Entity.Drilltype;
import com.etoile.app.Repository._Drilltype;

@Service
public class DrilltypeSvc {
	@Autowired _Drilltype _dt;
	
	public List<Drilltype> list(){
		try {
			List<Drilltype> arDT=_dt.findAll(Sort.by("typename"));
			return arDT;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public Drilltype get(int dtid) {
		try {
			Drilltype dt=_dt.findById(dtid).orElseThrow(()->new Exception("dtid not found"));
			return dt;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public boolean insert(String typename) {
		try {
			Drilltype dt = new Drilltype();
			dt.setTypename(typename);
			_dt.save(dt);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public boolean update(String typename,int dtid) {
		try {
			Drilltype dt=_dt.findById(dtid).orElseThrow(()->new Exception("drilltype not found"));
			dt.setTypename(typename);
			dt.setUpdated(LocalDateTime.now());
			_dt.save(dt);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());			
		}
		return false;
	}
	public boolean delete(int dtid) {
		try {
			_dt.deleteById(dtid);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
