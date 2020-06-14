package com.tulio.deltafitgym.model.enums;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumMonths {

	JANUARY(1),
	FEBRUARY(2),
	MARCH(3),
	APRIL(4),
	MAY(5),
	JUNE(6),
	JULY(7),
	AUGUST(8),
	SEPTEMBER(9),
	OCTOBER(10),
	NOVEMBER(11),
	DECEMBER(12);
	
	private int value;

	public int getValue() { return this.value; }
	
	public static EnumMonths recreateEnum(int value) {
		return Stream.of(EnumMonths.values()).filter(month -> month.getValue() == value).findFirst().orElse(null);
	}
}
