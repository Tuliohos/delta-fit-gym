package com.tulio.deltafitgym.model.dto;

import java.math.BigDecimal;

import com.tulio.deltafitgym.model.enums.EnumPaymentMethod;
import com.tulio.deltafitgym.model.enums.EnumPaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
	
	private Long cod;
	private String description;
	private String memberName;
	private String dateTimeRecord;
	private BigDecimal value;
	private EnumPaymentStatus status;
	private EnumPaymentMethod method;
}
