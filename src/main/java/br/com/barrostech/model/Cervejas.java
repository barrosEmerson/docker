package br.com.barrostech.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cervejas {

	@Id
	@SequenceGenerator(name = "cerveja_seq", sequenceName = "cerveja_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cerveja_seq")
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank(message = "cervejas-1")
	private String nome;
	@NotNull(message = "cervejas-2")
	private CervejaType type;

	@NotNull(message = "cervejas-3")
	@DecimalMin(value = "0", message = "cervejas-4")
	private BigDecimal volume;

	@JsonIgnore
	public boolean isNew() {
		return getId() == null;
	}
	@JsonIgnore
	public boolean alreadyExist() {
		return getId() != null;
	}

}
