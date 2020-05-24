package com.tulio.deltafitgym.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tulio.deltafitgym.model.Member;

public interface IMemberRepository extends JpaRepository<Member, Long>{
	
	public Optional<Member> findByPersonCpf(String cpf);
}
