package com.crestasom.dms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	private String name;
	private Double weight;
	private String code;
	private String imgPath;
}
