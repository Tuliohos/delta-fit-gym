package com.tulio.deltafitgym.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumPaymentStatus {
	
	PENDING(0, "Pendente"),
	EFFECTIVE(1, "Efetivado"),
	LATE(2, "Atrasado"),
	CANCELED(3, "Cancelado");
	
	private Integer value;
	private String description;
	
	public Integer getValue() { return this.value; }

	public String getDescription() { return description; }
	
}
