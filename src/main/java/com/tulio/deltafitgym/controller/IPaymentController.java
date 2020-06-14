package com.tulio.deltafitgym.controller;

import java.util.List;
import java.util.Optional;

import com.tulio.deltafitgym.model.Payment;
import com.tulio.deltafitgym.model.dto.MonthlyEarningsChartDTO;
import com.tulio.deltafitgym.model.dto.PaymentDTO;

public interface IPaymentController {

	public Payment save(Payment member);

	public Payment update(Payment member);

	public void cancel(Long memberCod);

	public List<PaymentDTO> loadList(Payment member);

	public Optional<Payment> findByCod(Long cod);

	public List<String> getYearsList();
	
	public List<MonthlyEarningsChartDTO> getMonthlyEarningsChartData(Integer yearFilter);

}
