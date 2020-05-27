package com.tulio.deltafitgym.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tulio.deltafitgym.model.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, Long>{

	public Optional<Employee> findByUserEmail(String email);
	
	public Optional<Employee> findByPersonCpf(String cpf);
}
