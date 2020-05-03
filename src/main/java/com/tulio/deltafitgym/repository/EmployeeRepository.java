package com.tulio.deltafitgym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tulio.deltafitgym.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
