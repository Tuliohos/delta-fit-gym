package com.tulio.deltafitgym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tulio.deltafitgym.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
