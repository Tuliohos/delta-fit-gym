package com.tulio.deltafitgym.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumPaymentStatus {
	
	PENDING(0),
	COMPLETED(1),
	CANCELED(2);
	
	private Integer value;
	
	public Integer getValue() { return this.value; }

}
