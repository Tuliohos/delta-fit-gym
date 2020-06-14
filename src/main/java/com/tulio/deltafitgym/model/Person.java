package com.tulio.deltafitgym.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tulio.deltafitgym.model.enums.EnumGender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="pessoa")
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod")
	private Long cod;
	
	@Column(name="nome", nullable = false)
	private String name;
	
	@Column(name="cpf", length = 14, nullable = false)
	private String cpf;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name="genero", nullable = false)
	private EnumGender gender;
	
	@Column(name="telefone")
	private String phoneNumber;
}
