package com.crestasom.dms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DroneDTO {
	private String serialNumber;
	private String model;
	private Double maxWeight;
	private Integer batteryCapacity;
	private String state;

}
