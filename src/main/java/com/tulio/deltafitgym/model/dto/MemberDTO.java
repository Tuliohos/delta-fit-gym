package com.tulio.deltafitgym.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

	private Long cod;
	private String name;
	private String cpf;
	private String membership;
	private String dateTimeRegistration;
	
}
