package com.tulio.deltafitgym.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tulio.deltafitgym.controller.IEmployeeController;
import com.tulio.deltafitgym.exception.LogicValidationException;
import com.tulio.deltafitgym.model.Employee;
import com.tulio.deltafitgym.model.Person;
import com.tulio.deltafitgym.model.User;

@RestController
@RequestMapping("/api/employee")
public class EmployeeResource {
	
	@Autowired
	private IEmployeeController controller;
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody Employee employee){
		try {
			Employee savedEmployee = controller.save(employee);
			return new ResponseEntity<Object>(savedEmployee, HttpStatus.CREATED);
		} catch (LogicValidationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<Object> update(@RequestBody Employee employee){
		try {
			Employee savedEmployee = controller.update(employee);
			return new ResponseEntity<Object>(savedEmployee, HttpStatus.OK);
		} catch (LogicValidationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long cod){
		try {
			controller.delete(cod);
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} catch (LogicValidationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Employee>> loadList(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "email", required = false) String email) {
		
		Person person = Person.builder().name(name).cpf(cpf).build();
		User user = User.builder().email(email).build();
		Employee employee = Employee.builder().person(person).user(user).build();
		
		List<Employee> employees = controller.loadList(employee);
		return ResponseEntity.ok(employees);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findByCod(@PathVariable("id") Long cod) {
		return controller.findByCod(cod)
				.map(employee -> new ResponseEntity<Object>(employee, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<Object>(HttpStatus.NOT_FOUND));
	}

}
