package com.etoile.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etoile.app.Entity.Drill;
import com.etoile.app.Entity.Drilltype;
import com.etoile.app.Repository._Drill;
import com.etoile.app.Repository._Drilltype;

@Service
public class DrillSvc {
	@Autowired _Drill _drl;
	@Autowired _Drilltype _dt;
	
	public Drill get(int did) {
		try {
			Drill drill = _drl.findById(did).orElseThrow(()->new Exception("Did not found"));
			return drill;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public boolean insert(String name,String comment,int dtypeID) {
		try {
			Drill drill= new Drill();
			drill.setName(name);
			drill.setComment(comment);
			Drilltype dt = _dt.findById(dtypeID).orElseThrow(()->new Exception("dtype id not found"));
			drill.setDrilltype(dt);
			_drl.save(drill);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public boolean update(String name,String comment,int dtypeID,int did) {
		try {
			Drill drill=_drl.findById(did).orElseThrow(()->new Exception("did not found"));
			drill.setName(name);
			drill.setComment(comment);
			Drilltype dt = _dt.findById(dtypeID).orElseThrow(()->new Exception("dtype id not found"));
			drill.setDrilltype(dt);
			_drl.save(drill);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public boolean delete(int did) {
		try {
			_drl.deleteById(did);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
