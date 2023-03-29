package com.crestasom.dms.repo;

import org.springframework.data.repository.CrudRepository;

import com.crestasom.dms.model.Drone;

public interface DroneRepo extends CrudRepository<Drone, Integer> {

	public Drone findBySerialNumber(String serialNumber);
}
