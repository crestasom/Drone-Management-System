package com.crestasom.dms.dto;

import com.crestasom.dms.validator.ValidModel;
import com.crestasom.dms.validator.ValidState;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DroneDTO implements BaseDTO {
	@Size(max = 100, message = "Max length for Serial Number is 100")
	private String serialNumber;
	@ValidModel
	private String model;
	@Max(value = 500, message = "Weight limit for drone is 500 Gram")
	private Double maxWeight;
	@Max(value = 100, message = "Battery capacity should be less than 100")
	@Min(value = 0, message = "Battery capacity should be greater than 0")
	private Integer batteryCapacity;
	@ValidState
	private String state;

}
