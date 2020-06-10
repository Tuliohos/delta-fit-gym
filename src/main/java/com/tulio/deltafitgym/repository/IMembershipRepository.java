package com.tulio.deltafitgym.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tulio.deltafitgym.model.Membership;

public interface IMembershipRepository extends JpaRepository<Membership, Long>{
	
	public Optional<Membership> findByDescription(String description);

}
