package com.crestasom.dms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneDTO {
	private String serialNumber;
	private String model;
	private Double maxWeight;
	private Integer batteryCapacity;
	private String state;

}
