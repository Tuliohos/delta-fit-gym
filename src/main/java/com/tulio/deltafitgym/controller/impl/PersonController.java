package com.tulio.deltafitgym.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tulio.deltafitgym.controller.IPersonController;
import com.tulio.deltafitgym.exception.LogicValidationException;
import com.tulio.deltafitgym.model.Person;
import com.tulio.deltafitgym.repository.IPersonRepository;

@Service
public class PersonController implements IPersonController{
	
	@Autowired 
	private IPersonRepository personRepository;

	@Override
	public Boolean existsByCpf(String cpf) {
		return personRepository.existsByCpf(cpf);
	}
	
	@Override
	public void validate(Person person){
		
		if(person == null){
			throw new LogicValidationException("Insira os dados pessoais.");
		}
		
		if(person.getName() == null || StringUtils.isEmpty(person.getName())){
			throw new LogicValidationException("Insira um nome válido.");
		}
		
		if(person.getCpf() == null || StringUtils.isEmpty(person.getCpf())){
			throw new LogicValidationException("Insira um CPF válido.");
		}
		
		if(person.getGender() == null){
			throw new LogicValidationException("Insira um gênero válido.");
		}
	}

}
