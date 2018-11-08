package com.sununiq.mapper;

import com.sununiq.domain.Employees;

public interface EmployeesMapper extends Mapper<Employees, Long> {

	int userDefined();
}
