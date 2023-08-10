package com.test.imageinnovate.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.imageinnovate.beans.Employee;
import com.test.imageinnovate.repo.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo repo;
	
	@Override
	public void addEmployee(Employee emp) {
		repo.save(emp);
	}
	
	
	private Map<Integer, Integer> getYearlySalaryDetailsOfAllEmployees() {
		List<Employee> listEmps = repo.findAll();
		Map<Integer, Integer> salaryMap = new HashMap<Integer, Integer>();
		for(Employee emp : listEmps) {
			int yearlySalary = 0;
			int months =getEmpMonthsOfCurrentYear(emp)>0?getEmpMonthsOfCurrentYear(emp):0;
			yearlySalary = emp.getSalary()*months + remainingDaysAmountOfEmp(emp);
			salaryMap.put(emp.getEmployeeId(), yearlySalary);
		}
		return salaryMap;
	}
	
	
	private Map<Integer, Double> getYearlyTaxDetails() {
		
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		for(Employee e : listEmps()) {
			int salary = e.getSalary()*12;
			System.out.println(salary);
			if(salary<=250000) {
				
				map.put(e.getEmployeeId(), 0.0);
			}else if(salary>250000 && salary<=500000){
				
				map.put(e.getEmployeeId(),((salary-250000)/100.0d)*5.0d);
			}else if(salary>500000 && salary <=1000000) {
				
				map.put(e.getEmployeeId(), ((salary-500000)/100.0d)*10.0d+12500);
			}else {
				
				map.put(e.getEmployeeId(), ((salary-100000)/100.0d)*20.0d+62500);
			}
		}
		return map;
	}
	
	
	private Map<Integer, Double> getCessDetails(Map<Integer, Integer> salaryMap) {
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		for(Map.Entry<Integer, Integer> e : salaryMap.entrySet()) {
			int sal = e.getValue();
			if(sal>2500000) {
				map.put(e.getKey(), (sal/100.0)*2);
			}else {
				map.put(e.getKey(), 0.0);
			}
		}
		return map;
	}
	
	private int getEmpMonthsOfCurrentYear(Employee emp) {
		LocalDate now = LocalDate.now();
		LocalDate ld = LocalDate.parse(emp.getDoj(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		if(ld.getYear() == now.getYear()) {
			return now.getMonthValue()-ld.getMonthValue()-1;
		}else {
			return now.getMonthValue()-4;
		}
	}
	
	private int remainingDaysAmountOfEmp(Employee emp) {
		LocalDate ld = LocalDate.parse(emp.getDoj(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return (emp.getSalary()/(ld.lengthOfMonth() - ld.getDayOfMonth()+1))+(emp.getSalary()/(LocalDate.now().getDayOfMonth()));
	}

	@Override
	public List<Map<String, String>> getYearlySalaryAndTaxDetails() {
		Map<Integer,Integer> yearlySalaryDetailsOfAllEmployees = getYearlySalaryDetailsOfAllEmployees();
		Map<Integer,Double> yearlyTaxDetails = getYearlyTaxDetails();
		Map<Integer,Double> cessDetails = getCessDetails(yearlySalaryDetailsOfAllEmployees);
		List<Map<String, String>> listOfMaps = new ArrayList<Map<String,String>>();
		
		List<Employee> emps = repo.findAll();
		for(Employee emp : emps) {
			Map<String, String> detailsMap = new HashMap<>();
			detailsMap.put("EmployeeId", emp.getEmployeeId()+"");
			detailsMap.put("First Name", emp.getFirstName());
			detailsMap.put("Last Name", emp.getLastName());
			detailsMap.put("Yearly Salary", yearlySalaryDetailsOfAllEmployees.get(emp.getEmployeeId())+"");
			detailsMap.put("Yearly Tax", yearlyTaxDetails.get(emp.getEmployeeId())+"");
			detailsMap.put("Cess Amount", cessDetails.get(emp.getEmployeeId())+"");
			listOfMaps.add(detailsMap);
		}
		
		return listOfMaps;
	}


	@Override
	public List<Employee> listEmps() {
		
		return repo.findAll();
	}
	
}
