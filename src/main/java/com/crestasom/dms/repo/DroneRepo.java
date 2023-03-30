package com.crestasom.dms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.crestasom.dms.model.Drone;
import com.crestasom.dms.model.enums.State;

public interface DroneRepo extends CrudRepository<Drone, Integer> {

	Drone findBySerialNumber(String serialNumber);

	 List<Drone> findByStateNotAndBatteryCapacityGreaterThan(State state, Integer batteryLevel);
}
