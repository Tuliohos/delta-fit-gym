package com.tulio.deltafitgym.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tulio.deltafitgym.controller.IPersonController;
import com.tulio.deltafitgym.repository.IPersonRepository;

@Service
public class PersonController implements IPersonController{
	
	@Autowired 
	private IPersonRepository personRepository;

	@Override
	public Boolean existsByCpf(String cpf) {
		return personRepository.existsByCpf(cpf);
	}

}
