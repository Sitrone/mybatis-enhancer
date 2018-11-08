package com.sununiq.controller;

import com.sununiq.domain.Employees;
import com.sununiq.service.IEmployeesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EmployeesController {

	@Autowired
	private IEmployeesService employeesService;

	@GetMapping("/employees/{id}")
	public Employees queryEmployee(@PathVariable Long id) {
		log.info("query employees {}.", id);
		Employees employees = employeesService.queryById(id);
		log.info("query result is :{}", employees);
		return employees;
	}
}
