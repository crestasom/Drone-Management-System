package com.crestasom.dms.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.crestasom.dms.dto.DroneDTO;
import com.crestasom.dms.dto.MedicationDTO;
import com.crestasom.dms.model.Drone;
import com.crestasom.dms.model.ResponseBean;
import com.crestasom.dms.model.response.CheckAvailableDroneResponse;
import com.crestasom.dms.model.response.CheckBatteryPercentageResponse;
import com.crestasom.dms.model.response.CheckMedicationResponse;
import com.crestasom.dms.repo.DroneRepo;
import com.crestasom.dms.service.DroneService;
import com.crestasom.dms.util.ConfigUtility;
import com.crestasom.dms.util.DTOUtility;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DroneServiceImpl implements DroneService {

	private DroneRepo droneRepo;
	private ConfigUtility configUtility;
	private static final Logger logger = LoggerFactory.getLogger(DroneServiceImpl.class);

	@Override
	public ResponseBean register(DroneDTO droneDto) {
		// TODO Auto-generated method stub
		logger.info("Received drone for registering [{}]", droneDto);
		ResponseBean resp = new ResponseBean();
		Drone drone = droneRepo.findBySerialNumber(droneDto.getSerialNumber());
		if (drone != null) {
			resp.setRespCode(configUtility.getPropertyAsInt("register.drone.duplicate.serial.number.resp.code", 400));
			resp.setRespDesc(configUtility.getProperty("register.drone.duplicate.serial.number.resp.desc",
					"Drone for serial number [" + droneDto.getSerialNumber() + "] already exists"));

		} else {
			drone = DTOUtility.convertDroneDTOToDrone(droneDto);
			droneRepo.save(drone);

			resp.setRespCode(configUtility.getPropertyAsInt("server.success.resp.code", 200));
			resp.setRespDesc(
					configUtility.getProperty("register.drone.success.resp.desc", "Drone Registration Success"));
		}

		logger.info("returning response [{}]", resp);
		return resp;

	}

	@Override
	public ResponseBean loadMedicationItems(String serialNumber, List<MedicationDTO> medicationItemList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckMedicationResponse checkLoadedMedication(String serialNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckAvailableDroneResponse checkAvailableDroneForLoading() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckBatteryPercentageResponse checkDroneBatteryLevel(String serialNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
