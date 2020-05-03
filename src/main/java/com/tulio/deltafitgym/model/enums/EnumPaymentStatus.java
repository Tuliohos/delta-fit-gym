package com.tulio.deltafitgym.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumPaymentStatus {
	PENDING("PENDENTE"),
	lATE("ATRASADO"), 
	EFFECTIVE("EFETIVADO");
	
	private String value;
	
	public String getValue() { return this.value; }
}
