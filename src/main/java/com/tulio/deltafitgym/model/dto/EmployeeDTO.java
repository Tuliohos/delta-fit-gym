package com.tulio.deltafitgym.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

	private Long cod;
	private String name;
	private String email;
	private String dateTimeHire;
	private String position;
}
