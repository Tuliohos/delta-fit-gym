package com.tulio.deltafitgym.controller;

import java.util.List;
import java.util.Optional;

import com.tulio.deltafitgym.model.Payment;

public interface IPaymentController {

	public Payment save(Payment member);

	public Payment update(Payment member);

	public void cancel(Long memberCod);

	public List<Payment> loadList(Payment member);

	public Optional<Payment> findByCod(Long cod);
}
