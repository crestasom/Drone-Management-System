package com.crestasom.dms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crestasom.dms.dto.DroneDTO;
import com.crestasom.dms.dto.MedicationDTO;
import com.crestasom.dms.model.ResponseBean;
import com.crestasom.dms.model.response.CheckAvailableDroneResponse;
import com.crestasom.dms.model.response.CheckBatteryPercentageResponse;
import com.crestasom.dms.model.response.CheckMedicationResponse;
import com.crestasom.dms.service.DroneService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class DroneController {

	private final DroneService service;

	@PostMapping("/register-drone")
	public ResponseBean registerDrone(@RequestBody DroneDTO drone) {
		return service.register(drone);
	}

	@PostMapping("/load-medication/{drone-serial-number}")
	public ResponseBean loadMedicationItems(@RequestBody List<MedicationDTO> medicationList,
			@PathVariable("drone-serial-number") String droneSerialNumber) {
		return service.loadMedicationItems(medicationList,droneSerialNumber);
	}

	@GetMapping("/check-loaded-medication")
	public CheckMedicationResponse checkLoadedMedication(@RequestParam(name = "serial-number") String serialNumber) {
		return service.checkLoadedMedication(serialNumber);
	}

	@GetMapping("/check-drone-battery-level")
	public CheckBatteryPercentageResponse checkDroneBatteryLevel(
			@RequestParam(name = "serial-number") String serialNumber) {
		return service.checkDroneBatteryLevel(serialNumber);
	}

	@GetMapping("/check-available-drones")
	public CheckAvailableDroneResponse checkAvailableDroneForLoading() {
		return service.checkAvailableDroneForLoading();
	}
}
