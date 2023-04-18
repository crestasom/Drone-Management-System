package com.crestasom.dms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class MedicationDTO implements BaseDTO {
	@Pattern(regexp = "^([A-Za-z0-9\\-\\_]+)$", message = "Only letters, numbers, dash(-) and underscore(_) is allowed in name")
	private String name;
	@NotNull(message = "weight is required")
	private Double weight;
	@Pattern(regexp = "^([A-Z0-9\\_]+)$", message = "Only upper case letter, numbers and underscore(_) is allowed in name")
	@NotNull(message = "Code should not be null")
	private String code;
	private String imgBase64;
}
