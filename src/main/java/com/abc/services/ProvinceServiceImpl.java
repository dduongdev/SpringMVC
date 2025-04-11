package com.abc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.dao.ProvinceDAO;
import com.abc.entities.Province;

@Service
public class ProvinceServiceImpl implements ProvinceService {
	private ProvinceDAO provinceDAO;
	
	@Autowired
	public ProvinceServiceImpl(ProvinceDAO provinceDAO) {
		this.provinceDAO = provinceDAO;
	}
	
	@Override
	public List<Province> getAll() {
		return provinceDAO.getAll();
	}

}
