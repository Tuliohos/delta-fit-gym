package com.tulio.deltafitgym.controller.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.tulio.deltafitgym.model.dto.EmployeeDTO;
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
	public List<EmployeeDTO> loadList(Employee employeeFilter) {
		Example<Employee> example = Example.of( employeeFilter, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher( StringMatcher.CONTAINING ) );
		
		List<Employee> employeeList = repository.findAll(example);
		return employeeDTOListBuilder(employeeList);
	}
	
	private List<EmployeeDTO> employeeDTOListBuilder(List<Employee> employeeList){
		
		List<EmployeeDTO> employeeDTOList = new ArrayList<>();
		employeeList.stream().forEach(employee -> {
			
			EmployeeDTO employeeDTO = EmployeeDTO.builder()
					.cod( employee.getCod() )
					.name( employee.getPerson().getName() )
					.email( employee.getUser() != null ? employee.getUser().getEmail() : "N/D")
					.dateTimeHire( employee.getDateTimeHire().format( DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss") ))
					.build();
			
			employeeDTOList.add(employeeDTO);
		});
		
		return employeeDTOList;
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
			if(employee.getUser() != null && StringUtils.isEmpty(employee.getUser().getEmail())) {
				throw new LogicValidationException("Insira o e-mail.");
			}
			
			if(employee.getUser() != null && StringUtils.isEmpty(employee.getUser().getPassword())) {
				throw new LogicValidationException("Insira a senha.");
			}
			
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
