package com.tulio.deltafitgym.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumPaymentType {

	CASH(0, "Dinheiro"),
	CREDIT_CARD(1, "Cartão de Crédito"),
	PICPAY(2, "Picpay");
	
	private Integer value;
	private String description;
	
	public Integer getValue() { return this.value; }

	public String getDescription() { return description; }
	
}
