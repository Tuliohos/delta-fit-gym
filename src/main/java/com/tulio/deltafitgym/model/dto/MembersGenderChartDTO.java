package com.tulio.deltafitgym.model.dto;

import com.tulio.deltafitgym.model.enums.EnumGender;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MembersGenderChartDTO {

	private EnumGender gender;
	private Long amount;
}
