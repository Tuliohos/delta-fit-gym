package com.tulio.deltafitgym.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumPaymentStatus {
	PENDING(0),
	EFFECTIVE(1),
	LATE(2),
	CANCELED(3);
	
	private Integer value;
	
	public Integer getValue() { return this.value; }
}
