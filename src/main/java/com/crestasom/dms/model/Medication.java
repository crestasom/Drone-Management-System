package com.crestasom.dms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medication")
public class Medication {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer medicationId;
	@Pattern(regexp = "^([A-Za-z0-9\\-\\_]+)$", message = "Only letters, numbers, dash(-) and underscore(_) is allowed in name")
	private String name;
	private Double weight;
	@Pattern(regexp = "^([A-Z0-9\\_]+)$", message = "Only upper case letter, numbers and underscore(_) is allowed in name")
	private String code;
	private String imgPath;
}
