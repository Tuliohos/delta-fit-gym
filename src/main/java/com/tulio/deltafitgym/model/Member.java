package com.tulio.deltafitgym.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="membro")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod")
	private Long cod;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cod_pessoa")
	private Person person;
	
	@ManyToOne
	@JoinColumn(name="cod_plano")
	private Membership membership;
	
	@Column(name = "dh_cadastro")
	private Date dateTimeRegistration;
	
}
