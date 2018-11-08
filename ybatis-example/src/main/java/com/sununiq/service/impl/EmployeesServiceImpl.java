package com.sununiq.service.impl;

import com.sununiq.domain.Employees;
import com.sununiq.mapper.EmployeesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sununiq.service.IEmployeesService;

@Service
public class EmployeesServiceImpl implements IEmployeesService {

	@Autowired
	private EmployeesMapper employeesMapper;

	@Override
	public Employees queryById(Long id) {
		return employeesMapper.selectById(id);
	}
}
