package com.tulio.deltafitgym.controller.impl;

import java.time.LocalDateTime;
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
import com.tulio.deltafitgym.exception.AuthenticationErrorException;
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
	public Employee signIn(String email, String password) {
		Optional<Employee> employee = repository.findByUserEmail(email);
		
		if(!employee.isPresent()) {
			throw new AuthenticationErrorException("Usuário não encontrado para o e-mail informado.");
		}
		
		if(!employee.get().getUser().getPassword().equals(password)) {
			throw new AuthenticationErrorException("Senha inválida.");
		}
		
		return employee.get();
	}
	
	@Override
	@Transactional
	public Employee save(Employee employee) {
		this.validate(employee);
		employee.setDateTimeHire(LocalDateTime.now());
		return repository.save(employee);
	}
	
	@Override
	@Transactional
	public Employee update(Employee employee) {
		this.validate(employee);
		Objects.requireNonNull(employee.getCod(), "Erro ao atualizar funcionário.");
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
			Optional<Employee> existingEmployee = repository.findByPersonCpf((employee.getPerson().getCpf()));
			if(employee.getCod() == null || existingEmployee.isPresent() && existingEmployee.get().getCod() != employee.getCod()) {				
				throw new LogicValidationException("Já existe um usuário cadastrado com este CPF.");
			}
		}
		
		if(employee.getUser() != null) {
			Optional<Employee> existingEmployee = repository.findByUserEmail(employee.getUser().getEmail());
			if(existingEmployee.isPresent() && !existingEmployee.get().getCod().equals(employee.getCod())) {
				throw new LogicValidationException("Este e-mail já está cadastrado.");
			}
		}
		
		if(employee.getSalary() == null){
			throw new LogicValidationException("Insira um salário válido.");
		}
		
	}
}
