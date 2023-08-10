package com.test.imageinnovate.service;

import java.util.List;
import java.util.Map;

import com.test.imageinnovate.beans.Employee;


public interface EmployeeService {
	
	public void addEmployee(Employee emp);
	
	public List<Map<String, String>>  getYearlySalaryAndTaxDetails();
	
	public List<Employee> listEmps();
}
