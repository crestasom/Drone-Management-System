package com.crestasom.dms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crestasom.dms.dto.DroneDTO;
import com.crestasom.dms.model.ResponseBean;
import com.crestasom.dms.model.request.LoadMedicationItemsRequest;
import com.crestasom.dms.model.response.CheckMedicationResponse;
import com.crestasom.dms.service.DroneService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class DroneController {

	private DroneService service;

	@PostMapping("/register-drone")
	public ResponseBean registerDrone(@RequestBody DroneDTO drone) {
		return service.register(drone);
	}

	@PostMapping("/load-medication")
	public ResponseBean loadMedicationItems(@RequestBody LoadMedicationItemsRequest request) {
		return service.loadMedicationItems(request);
	}

	@GetMapping("/check-loaded-medication")
	public CheckMedicationResponse checkLoadedMedication(@RequestParam(name = "serial-number") String serialNumber) {
		return service.checkLoadedMedication(serialNumber);
	}
}
