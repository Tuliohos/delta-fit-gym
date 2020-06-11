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

import com.tulio.deltafitgym.controller.IMembershipController;
import com.tulio.deltafitgym.exception.LogicValidationException;
import com.tulio.deltafitgym.model.Membership;
import com.tulio.deltafitgym.repository.IMembershipRepository;

@Service
public class MembershipController implements IMembershipController{

	@Autowired
	private IMembershipRepository repository;
	
	@Override
	@Transactional
	public Membership save(Membership membership) {
		this.validate(membership);
		membership.setDateTimeRegistration(LocalDateTime.now());
		return repository.save(membership);
	}
	
	@Override
	@Transactional
	public Membership update(Membership membership) {
		Objects.requireNonNull(membership.getCod(), "Erro ao atualizar plano.");
		this.validate(membership);
		return repository.save(membership);
	}

	@Override
	@Transactional
	public void delete(Long membershipCod) {
		Optional<Membership> membershipForDelete = this.findByCod(membershipCod);
		if(membershipForDelete.isPresent()) {
			repository.delete(membershipForDelete.get());
		}else{
			throw new LogicValidationException("Usuário não encontrado.");
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Membership> loadList(Membership membership) {
		Example<Membership> example = Example.of( membership, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher( StringMatcher.CONTAINING ) );
		
		return repository.findAll(example);
	}
	
	@Override
	public Optional<Membership> findByCod(Long cod){
		return repository.findById(cod);
	}
	
	private void validate(Membership membership) {
		
		if(membership.getDescription() == null || StringUtils.isEmpty(membership.getDescription())) {
			throw new LogicValidationException("Informe uma descrição válida");
		}
		
		Optional<Membership> existingMembership = repository.findByDescription(membership.getDescription());
		if(existingMembership.isPresent() && (membership.getCod() == null || existingMembership.isPresent() && existingMembership.get().getCod() != membership.getCod())) {				
			throw new LogicValidationException("Já existe um plano de usuário cadastrado com esta descrição.");
		}
		
		if(membership.getPrice() == null) {
			throw new LogicValidationException("Informe um preço válido");
		}
		
		if(membership.getPrice() > Double.valueOf(9000)) {
			throw new LogicValidationException("Preço muito alto para um plano de usuário.");
		}
	}
}
