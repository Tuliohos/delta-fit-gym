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

import com.tulio.deltafitgym.controller.IMembershipController;
import com.tulio.deltafitgym.exception.LogicValidationException;
import com.tulio.deltafitgym.model.Membership;

@RestController
@RequestMapping("/api/membership")
public class MembershipResource {

	@Autowired
	private IMembershipController controller;
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody Membership membership){
		try {
			Membership savedMembership = controller.save(membership);
			return new ResponseEntity<Object>(savedMembership, HttpStatus.CREATED);
		} catch (LogicValidationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<Object> update(@RequestBody Membership membership){
		try {
			Membership savedMembership = controller.update(membership);
			return new ResponseEntity<Object>(savedMembership, HttpStatus.OK);
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
	public ResponseEntity<List<Membership>> loadList(
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "price", required = false) Double price) {
		
		Membership membership = Membership.builder().description(description).price(price).build();
		
		List<Membership> memberships = controller.loadList(membership);
		return ResponseEntity.ok(memberships);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findByCod(@PathVariable("id") Long cod) {
		return controller.findByCod(cod)
				.map(membership -> new ResponseEntity<Object>(membership, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<Object>(HttpStatus.NOT_FOUND));
	}
}
