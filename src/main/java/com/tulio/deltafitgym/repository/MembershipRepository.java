package com.tulio.deltafitgym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tulio.deltafitgym.model.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Long>{

}
