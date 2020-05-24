package com.tulio.deltafitgym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tulio.deltafitgym.model.Person;

public interface IPersonRepository extends JpaRepository<Person, Long>{

	public Boolean existsByCpf(String cpf);
	
}
