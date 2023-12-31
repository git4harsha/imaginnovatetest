package com.test.imageinnovate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.imageinnovate.beans.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

}
