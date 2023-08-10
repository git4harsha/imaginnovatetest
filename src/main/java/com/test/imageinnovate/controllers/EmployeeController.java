package com.test.imageinnovate.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.imageinnovate.beans.Employee;
import com.test.imageinnovate.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeService empService;
	 
	@PostMapping("/addEmployee")
	public ResponseEntity<?> addEmployee(@Valid @RequestBody Employee employee) {
		empService.addEmployee(employee);
		return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
	}
	
	@GetMapping("/getTaxDetails")
	public List<Map<String, String>> getTaxDeductionDetails(){
		return empService.getYearlySalaryAndTaxDetails();
	}
	
	@GetMapping("/listAll")
	public List<Employee> listAllEmps(){
		return empService.listEmps();
	}
	
}
