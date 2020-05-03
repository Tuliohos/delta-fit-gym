package com.tulio.deltafitgym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tulio.deltafitgym.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
