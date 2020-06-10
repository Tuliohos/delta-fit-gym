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

import com.tulio.deltafitgym.controller.IMemberController;
import com.tulio.deltafitgym.controller.IPersonController;
import com.tulio.deltafitgym.exception.LogicValidationException;
import com.tulio.deltafitgym.model.Member;
import com.tulio.deltafitgym.repository.IMemberRepository;

@Service
public class MemberController implements IMemberController{
	
	@Autowired
	private IMemberRepository repository;
	
	@Autowired
	private IPersonController personController;

	@Override
	@Transactional
	public Member save(Member member) {
		this.validate(member);
		member.setDateTimeRegistration(LocalDateTime.now());
		return repository.save(member);
	}
	
	@Override
	@Transactional
	public Member update(Member member) {
		this.validate(member);
		Objects.requireNonNull(member.getCod(), "Erro ao atualizar membro.");
		return repository.save(member);
	}

	@Override
	@Transactional
	public void delete(Long memberCod) {
		Optional<Member> memberForDelete = this.findByCod(memberCod);
		if(memberForDelete.isPresent()) {
			repository.delete(memberForDelete.get());
		}else{
			throw new LogicValidationException("Usuário não encontrado.");
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Member> loadList(Member member) {
		Example<Member> example = Example.of( member, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher( StringMatcher.CONTAINING ) );
		
		return repository.findAll(example);
	}
	
	@Override
	public Optional<Member> findByCod(Long cod){
		return repository.findById(cod);
	}
	
	private void validate(Member member) {
		
		if(member.getPerson() == null){
			throw new LogicValidationException("Insira os dados pessoais.");
		}
		
		if(member.getPerson().getName() == null || StringUtils.isEmpty(member.getPerson().getName())){
			throw new LogicValidationException("Insira um nome válido.");
		}
		
		if(member.getPerson().getCpf() == null || StringUtils.isEmpty(member.getPerson().getCpf())){
			throw new LogicValidationException("Insira um CPF válido.");
		}
		
		if(personController.existsByCpf(member.getPerson().getCpf())) {
			Optional<Member> existingMember = repository.findByPersonCpf(member.getPerson().getCpf());
			if(member.getCod() == null || existingMember.isPresent() && existingMember.get().getCod() != member.getCod()) {				
				throw new LogicValidationException("Já existe um usuário cadastrado com este CPF.");
			}
		}
		
		if(member.getMembership() == null || member.getMembership().getCod() == null){
			throw new LogicValidationException("Selecione um plano.");
		}
	}

}
