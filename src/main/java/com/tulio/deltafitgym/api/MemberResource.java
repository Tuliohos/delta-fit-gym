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

import com.tulio.deltafitgym.controller.IMemberController;
import com.tulio.deltafitgym.exception.LogicValidationException;
import com.tulio.deltafitgym.model.Member;
import com.tulio.deltafitgym.model.Membership;
import com.tulio.deltafitgym.model.Person;
import com.tulio.deltafitgym.model.dto.MemberDTO;

@RestController
@RequestMapping("/api/member")
public class MemberResource {
	
	@Autowired
	private IMemberController controller;
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody Member member){
		try {
			Member savedMember = controller.save(member);
			return new ResponseEntity<Object>(savedMember, HttpStatus.CREATED);
		} catch (LogicValidationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<Object> update(@RequestBody Member member){
		try {
			Member savedMember = controller.update(member);
			return new ResponseEntity<Object>(savedMember, HttpStatus.OK);
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
	public ResponseEntity<List<MemberDTO>> loadList(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "membership", required = false) String membershipDescription) {
		
		Person person = Person.builder().name(name).cpf(cpf).build();
		Membership membership = Membership.builder().description(membershipDescription).build();
		Member member = Member.builder().person(person).membership(membership).build();
		
		List<MemberDTO> members = controller.loadList(member);
		return ResponseEntity.ok(members);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findByCod(@PathVariable("id") Long cod) {
		return controller.findByCod(cod)
				.map(member -> new ResponseEntity<Object>(member, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<Object>(HttpStatus.NOT_FOUND));
	}
}
