package com.tulio.deltafitgym.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumPaymentStatus {
	
	PENDING(0, "Pendente"),
	COMPLETED(1, "Efetivado"),
	CANCELED(2, "Cancelado");
	
	private Integer value;
	private String description;
	
	public Integer getValue() { return this.value; }

	public String getDescription() { return description; }
	
}
