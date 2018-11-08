package com.sununiq.service;

import com.sununiq.domain.Employees;

public interface IEmployeesService {
	Employees queryById(Long id);
}
