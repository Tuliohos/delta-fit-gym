package com.tulio.deltafitgym.controller.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tulio.deltafitgym.controller.IMemberController;
import com.tulio.deltafitgym.controller.IPaymentController;
import com.tulio.deltafitgym.exception.LogicValidationException;
import com.tulio.deltafitgym.model.Payment;
import com.tulio.deltafitgym.model.dto.PaymentDTO;
import com.tulio.deltafitgym.model.enums.EnumPaymentStatus;
import com.tulio.deltafitgym.repository.IPaymentRepository;

@Service
public class PaymentController implements IPaymentController{

	@Autowired
	private IPaymentRepository repository;
	
	@Autowired
	private IMemberController memberController;
	
	@Override
	@Transactional
	public Payment save(Payment payment) {
		this.validate(payment);
		payment.setDateTimeRecord(LocalDateTime.now());
		return repository.save(payment);
	}
	
	@Override
	@Transactional
	public Payment update(Payment payment) {
		this.validate(payment);
		Objects.requireNonNull(payment.getCod(), "Erro ao atualizar pagamento.");
		return repository.save(payment);
	}

	@Override
	@Transactional
	public void cancel(Long paymentCod) {
		Optional<Payment> paymentOptional = this.findByCod(paymentCod);
		if(paymentOptional.isPresent()) {
			Payment payment = paymentOptional.get();
			payment.setStatus(EnumPaymentStatus.CANCELED);
			repository.save(payment);
		}else{
			throw new LogicValidationException("Pagamento não encontrado.");
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PaymentDTO> loadList(Payment paymentFilter) {
		Example<Payment> example = Example.of( paymentFilter, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher( StringMatcher.CONTAINING ) );
		
		List<Payment> paymentList = repository.findAll(example);
		return paymentDTOListBuilder(paymentList);
	}
	
	private List<PaymentDTO> paymentDTOListBuilder(List<Payment> paymentList){
		
		List<PaymentDTO> paymentDTOList = new ArrayList<>();
		if(paymentList != null && Boolean.FALSE.equals(paymentList.isEmpty())) {
			paymentList.stream().forEach( payment -> {
				
				PaymentDTO paymentdto = PaymentDTO.builder()
						.cod(payment.getCod())
						.description(payment.getDescription())
						.memberName(payment.getMember().getPerson().getName())
						.dateTimeRecord( payment.getDateTimeRecord().format( DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss") ))
						.value( payment.getValue())
						.status( payment.getStatus().getDescription())
						.type( payment.getType().getDescription() )
						.build();
				
				paymentDTOList.add(paymentdto);
			});
		}
		
		return paymentDTOList;
	}
	
	@Override
	public Optional<Payment> findByCod(Long cod){
		return repository.findById(cod);
	}
	
	private void validate(Payment payment) {
		
		if(payment.getMember() == null 
				|| payment.getMember().getCod() == null
				|| !memberController.findByCod(payment.getMember().getCod()).isPresent()){
			throw new LogicValidationException("Pagamento com membro inválido");
		}
		
		if(payment.getValue() == null){
			throw new LogicValidationException("Informe um valor válido");
		}
		
		if(payment.getStatus() == null){
			throw new LogicValidationException("Pagamento com status inválido");
		}
	}
}
