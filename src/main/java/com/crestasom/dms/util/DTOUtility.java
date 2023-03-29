package com.crestasom.dms.util;

import com.crestasom.dms.dto.DroneDTO;
import com.crestasom.dms.model.Drone;
import com.crestasom.dms.model.enums.Model;
import com.crestasom.dms.model.enums.State;

import lombok.Generated;

public class DTOUtility {
	@Generated
	private DTOUtility() {

	}

	public static Drone convertDroneDTOToDrone(DroneDTO dto) {
		return Drone.builder()
				.serialNumber(dto.getSerialNumber())
				.model(Model.getModel(dto.getModel()))
				.maxWeight(dto.getMaxWeight())
				.batteryCapacity(dto.getBatteryCapacity())
				.state(State.getState(dto.getState())).build();
	}

}
