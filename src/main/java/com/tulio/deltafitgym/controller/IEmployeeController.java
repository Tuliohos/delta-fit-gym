package com.tulio.deltafitgym.controller;

import java.util.List;
import java.util.Optional;

import com.tulio.deltafitgym.model.Employee;
import com.tulio.deltafitgym.model.dto.EmployeeDTO;

public interface IEmployeeController {

	public Employee save(Employee member);

	public Employee update(Employee member);

	public void delete(Long memberCod);

	public List<EmployeeDTO> loadList(Employee member);

	public Optional<Employee> findByCod(Long cod);

	public Employee signIn(String email, String password);
}
