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

import com.tulio.deltafitgym.controller.IPaymentController;
import com.tulio.deltafitgym.exception.LogicValidationException;
import com.tulio.deltafitgym.model.Member;
import com.tulio.deltafitgym.model.Payment;
import com.tulio.deltafitgym.model.Person;
import com.tulio.deltafitgym.model.enums.EnumPaymentStatus;

@RestController
@RequestMapping("/api/payment")
public class PaymentResource {
	
	@Autowired
	private IPaymentController controller;
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody Payment payment){
		try {
			Payment savedPayment = controller.save(payment);
			return new ResponseEntity<Object>(savedPayment, HttpStatus.CREATED);
		} catch (LogicValidationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<Object> update(@RequestBody Payment payment){
		try {
			Payment savedPayment = controller.update(payment);
			return new ResponseEntity<Object>(savedPayment, HttpStatus.OK);
		} catch (LogicValidationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> cancel(@PathVariable("id") Long cod){
		try {
			controller.cancel(cod);
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} catch (LogicValidationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Payment>> loadList(
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "memberName", required = false) String memberName,
			@RequestParam(value = "memberCpf", required = false) String memberCpf,
			@RequestParam(value = "dateTimeRecord", required = false) String dateTimeRecord) {
		
		Person person = Person.builder().name(memberName).cpf(memberCpf).build();
		Member member = Member.builder().person(person).build();
		Payment payment = Payment.builder().member(member).status(EnumPaymentStatus.valueOf(status)).build();
		
		List<Payment> payments = controller.loadList(payment);
		return ResponseEntity.ok(payments);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findByCod(@PathVariable("id") Long cod) {
		return controller.findByCod(cod)
				.map(payment -> new ResponseEntity<Object>(payment, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<Object>(HttpStatus.NOT_FOUND));
	}

}
