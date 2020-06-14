package com.tulio.deltafitgym.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="funcionario")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod")
	private Long cod;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cod_pessoa")
	private Person person;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cod_usuario")
	private User user;
	
	@Column(name = "dh_contrato")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime dateTimeHire;
	
	@Column(name="salario")
	private BigDecimal salary;
	
	@Column(name="cargo")
	private String position;

}
