package com.tulio.deltafitgym.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

import com.tulio.deltafitgym.model.enums.EnumPaymentStatus;
import com.tulio.deltafitgym.model.enums.EnumPaymentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="pagamento")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod")
	private Long cod;
	
	@Column(name="descricao", nullable = false)
	private String description;
	
	@OneToOne
	@JoinColumn(name="cod_membro", nullable = false)
	private Member member;
	
	@Column(name = "dh_registro")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime dateTimeRecord;
	
	@Column(name="valor", nullable = false)
	private BigDecimal value;
	
	@Column(name="status", nullable = false)
	private EnumPaymentStatus status;
	
	@Column(name="tipo", nullable = false)
	private EnumPaymentType type;
}
