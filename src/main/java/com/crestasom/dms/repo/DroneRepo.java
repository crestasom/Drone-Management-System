package com.crestasom.dms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crestasom.dms.model.Drone;
import com.crestasom.dms.model.enums.State;

public interface DroneRepo extends JpaRepository<Drone, Integer> {

	Drone findBySerialNumber(String serialNumber);

	 List<Drone> findByStateNotAndBatteryCapacityGreaterThan(State state, Integer batteryLevel);
}
