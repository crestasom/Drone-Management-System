package com.crestasom.dms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medication")
@Builder
public class Medication {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer medicationId;
	@Pattern(regexp = "^([A-Za-z0-9\\-\\_]+)$", message = "Only letters, numbers, dash(-) and underscore(_) is allowed in name")
	@NotNull(message = "Name should not be null")
	private String name;
	@NotNull(message = "weight is required")
	private Double weight;
	@Pattern(regexp = "^([A-Z0-9\\_]+)$", message = "Only upper case letter, numbers and underscore(_) is allowed in name")
	@NotNull(message = "Code should not be null")
	private String code;
	private String imgPath;
	@Transient
	private String imgData;
}
