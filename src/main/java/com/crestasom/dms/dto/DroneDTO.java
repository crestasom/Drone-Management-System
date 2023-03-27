package com.crestasom.dms.dto;

import com.crestasom.dms.model.enums.Model;
import com.crestasom.dms.model.enums.State;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneDTO {
	private String serialNumber;
	private Model model;
	private Double maxWeight;
	private Integer batteryCapacity;
	private State state;

}
