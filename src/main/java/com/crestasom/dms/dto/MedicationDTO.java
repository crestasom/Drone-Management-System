package com.crestasom.dms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationDTO {
	private String name;
	private Double weight;
	private String code;
	private String imgBase64;
}
