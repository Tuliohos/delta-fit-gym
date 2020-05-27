package com.tulio.deltafitgym.controller;

import java.util.List;
import java.util.Optional;

import com.tulio.deltafitgym.model.Employee;

public interface IEmployeeController {

	public Employee save(Employee member);

	public Employee update(Employee member);

	public void delete(Long memberCod);

	public List<Employee> loadList(Employee member);

	public Optional<Employee> findByCod(Long cod);

	public Employee signIn(String email, String password);
}
