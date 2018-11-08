package com.sununiq.mapper;

import com.sununiq.domain.Employees;
import com.sununiq.entity.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeesMapperTest {

	@Autowired
	private EmployeesMapper employeesMapper;

	@Test
	public void userDefined() {
		log.warn(employeesMapper.selectById(10001L).toString());
		log.warn(String.valueOf(employeesMapper.userDefined()));

		Employees employees1 = new Employees();
		Employees employees2 = new Employees();
		Employees employees3 = new Employees();

		//	  employees1.setEmpNo(10001);
		employees2.setEmpNo(10002);
		employees3.setEmpNo(10003);
		log.warn(String.valueOf(employeesMapper.selectPage(Page.Companion.start(1L, 10L, employees1, "emp_no"))));
	}
}
