package com.tulio.deltafitgym.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumPaymentMethod {

	CASH(0),
	CREDIT_CARD(1),
	PICPAY(2);
	
	private Integer value;
	
	public Integer getValue() { return this.value; }
	
}
