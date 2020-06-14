package com.tulio.deltafitgym.model.dto;

import java.math.BigDecimal;

import com.tulio.deltafitgym.model.enums.EnumMonths;

import lombok.Data;

@Data
public class MonthlyEarningsChartDTO {
	
	private EnumMonths month;
		
	private BigDecimal value;
	 
	public MonthlyEarningsChartDTO(int monthNumber, BigDecimal value) {
		super();
		this.month = EnumMonths.recreateEnum(monthNumber);
		this.value = value;
	}

	
}
