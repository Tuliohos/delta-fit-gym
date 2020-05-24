package com.tulio.deltafitgym.controller.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tulio.deltafitgym.controller.IEmployeeController;
import com.tulio.deltafitgym.controller.IPersonController;
import com.tulio.deltafitgym.exception.LogicValidationException;
import com.tulio.deltafitgym.model.Employee;
import com.tulio.deltafitgym.repository.IEmployeeRepository;

@Service
public class EmployeeController implements IEmployeeController{

	@Autowired
	private IEmployeeRepository repository;
	
	@Autowired
	private IPersonController personController;
	
	@Override
	@Transactional
	public Employee save(Employee employee) {
		this.validate(employee);
		return repository.save(employee);
	}
	
	@Override
	@Transactional
	public Employee update(Employee employee) {
		this.validate(employee);
		Objects.requireNonNull(employee.getCod(), "Erro ao atualizar funcionário.");
		employee.setDateTimeHire(new Date());
		return repository.save(employee);
	}

	@Override
	@Transactional
	public void delete(Long employeeCod) {
		Optional<Employee> employeeForDelete = this.findByCod(employeeCod);
		if(employeeForDelete.isPresent()) {
			repository.delete(employeeForDelete.get());
		}else{
			throw new LogicValidationException("Usuário não encontrado.");
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Employee> loadList(Employee employee) {
		Example<Employee> example = Example.of( employee, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher( StringMatcher.CONTAINING ) );
		
		return repository.findAll(example);
	}
	
	@Override
	public Optional<Employee> findByCod(Long cod){
		return repository.findById(cod);
	}
	
	private void validate(Employee employee) {
		
		Objects.requireNonNull(employee);
		
		if(employee.getPerson() == null){
			throw new LogicValidationException("Insira os dados pessoais.");
		}
		
		if(employee.getPerson() == null){
			throw new LogicValidationException("Insira os dados pessoais.");
		}
		
		if(employee.getPerson().getName() == null || StringUtils.isEmpty(employee.getPerson().getName())){
			throw new LogicValidationException("Insira um nome válido.");
		}
		
		if(employee.getPerson().getCpf() == null || StringUtils.isEmpty(employee.getPerson().getCpf())){
			throw new LogicValidationException("Insira um CPF válido.");
		}
		
		if(personController.existsByCpf(employee.getPerson().getCpf())) {
			throw new LogicValidationException("Já existe um usuário cadastrado com este CPF.");
		}
		
		if(employee.getSalary() == null){
			throw new LogicValidationException("Insira um salário válido.");
		}
		
	}
}
