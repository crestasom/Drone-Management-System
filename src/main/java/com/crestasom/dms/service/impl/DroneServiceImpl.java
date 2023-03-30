package com.crestasom.dms.service.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.crestasom.dms.dto.DroneDTO;
import com.crestasom.dms.dto.MedicationDTO;
import com.crestasom.dms.exception.DroneNotFoundException;
import com.crestasom.dms.exception.DuplicateDroneException;
import com.crestasom.dms.exception.ImageStoreException;
import com.crestasom.dms.exception.NoMedicationListFoundException;
import com.crestasom.dms.model.Drone;
import com.crestasom.dms.model.Medication;
import com.crestasom.dms.model.ResponseBean;
import com.crestasom.dms.model.enums.State;
import com.crestasom.dms.model.request.LoadMedicationItemsRequest;
import com.crestasom.dms.model.response.CheckAvailableDroneResponse;
import com.crestasom.dms.model.response.CheckBatteryPercentageResponse;
import com.crestasom.dms.model.response.CheckMedicationResponse;
import com.crestasom.dms.repo.DroneRepo;
import com.crestasom.dms.service.DroneService;
import com.crestasom.dms.util.ConfigUtility;
import com.crestasom.dms.util.DMSUtils;
import com.crestasom.dms.util.DTOUtility;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DroneServiceImpl implements DroneService {

	private DroneRepo droneRepo;
	private ConfigUtility configUtility;
	private static final Logger logger = LoggerFactory.getLogger(DroneServiceImpl.class);
	private static final String SUCCESS_DESC = "Success";

	@Override
	public ResponseBean register(DroneDTO droneDto) {
		logger.info("Received drone for registering [{}]", droneDto);
		ResponseBean resp = new ResponseBean();
		Drone drone = droneRepo.findBySerialNumber(droneDto.getSerialNumber());
		if (drone != null) {
			throw new DuplicateDroneException(
					String.format(configUtility.getProperty("register.drone.duplicate.serial.number.resp.code",
							"Drone for serial number %s already exists"), droneDto.getSerialNumber()));

		}
		drone = DTOUtility.convertDroneDTOToDrone(droneDto);
		droneRepo.save(drone);

		resp.setRespCode(200);
		resp.setRespDesc(configUtility.getProperty("register.drone.success.resp.desc", "Drone Registration Success"));

		logger.info("returning response [{}]", resp);
		return resp;

	}

	@Override
	@Transactional
	public ResponseBean loadMedicationItems(LoadMedicationItemsRequest request) {
		logger.info("Received LoadMedicationItemsRequest [{}]", request);
		ResponseBean resp = new ResponseBean();
		if (request.getMedicationItemList() == null || request.getMedicationItemList().size() == 0) {
			throw new NoMedicationListFoundException(configUtility.getProperty("load.medication.list.empty.resp.desc",
					"Medication list is empty in request"));
		}
		Drone drone = getDrone(request.getDroneSerialNumber());

		String rootPath = configUtility.getProperty("img.store.path");

		logger.debug("drone record [{}]", drone);
		Integer respCode = validateDroneForLoading(request, drone);
		logger.debug("validateDroneForLoading respCode [{}]", respCode);
		if (respCode != 101) {
			resp.setRespCode(respCode);
			resp.setRespDesc(String.format(
					configUtility.getProperty("load.medication.drone.loading.resp.desc." + respCode,
							"Failed while loading medication on Drone with serial number %s"),
					request.getDroneSerialNumber()));
			return resp;
		}
		List<Medication> medicationList = request.getMedicationItemList().stream()
				.map(medicationDto -> DTOUtility.convertMedicationDtoToMedication(medicationDto, rootPath)).toList();
		logger.info("Converted Medication List [{}]", medicationList);
		drone.getMedicationList().addAll(medicationList);
		droneRepo.save(drone);
		storeImgToFileSystem(medicationList);
		resp.setRespCode(200);
		resp.setRespDesc(String.format(
				configUtility.getProperty("load.medication.drone.success.resp.desc",
						"Drone with serial number %s is loaded with provided medication information"),
				request.getDroneSerialNumber()));

		return resp;
	}

	@Override
	public CheckMedicationResponse checkLoadedMedication(String serialNumber) {

		Drone drone = getDrone(serialNumber);
		CheckMedicationResponse resp = new CheckMedicationResponse();
		resp.setRespCode(200);
		resp.setRespDesc(String.format(
				configUtility.getProperty("check.loaded.medication.success.resp.desc", SUCCESS_DESC), serialNumber));
		if (drone.getMedicationList() == null || drone.getMedicationList().size() == 0) {
			return resp;
		}
		List<MedicationDTO> medList = drone.getMedicationList().stream()
				.map(DTOUtility::convertMedicationToMedicationDto).toList();
		resp.setMedicationList(medList);
		return resp;
	}

	@Override
	public CheckAvailableDroneResponse checkAvailableDroneForLoading() {
		List<Drone> droneList = droneRepo.findByStateNotAndBatteryCapacityGreaterThan(State.LOADING,
				configUtility.getPropertyAsInt("drone.low.battery", 25));
		CheckAvailableDroneResponse resp = new CheckAvailableDroneResponse();
		resp.setRespCode(200);
		resp.setRespDesc(configUtility.getProperty("check.available.drone.success.resp.desc", SUCCESS_DESC));
		resp.setDroneList(droneList.stream().filter(d -> isDroneLoadable(null, d))
				.map(DTOUtility::convertDroneToDroneDto).toList());
		return resp;
	}

	@Override
	public CheckBatteryPercentageResponse checkDroneBatteryLevel(String serialNumber) {
		Drone drone = getDrone(serialNumber);
		CheckBatteryPercentageResponse resp = new CheckBatteryPercentageResponse();
		resp.setRespCode(200);
		resp.setRespDesc(configUtility.getProperty("check.battery.percentage.success.resp.desc", SUCCESS_DESC));
		resp.setDroneBatteryLevel(drone.getBatteryCapacity());
		resp.setSerialNumber(serialNumber);
		return resp;
	}

	@Override
	public void removeAllDrone() {
		droneRepo.deleteAll();
	}

	private void storeImgToFileSystem(List<Medication> medicationList) {
		for (Medication medication : medicationList) {
			try {
				DMSUtils.storeImgToFile(medication.getImgPath(), medication.getImgData());
			} catch (IOException e) {
				logger.error("Exception : [{}]", e.getMessage(), e);
				throw new ImageStoreException(configUtility.getProperty("image.store.exception",
						"Problem in storing image. Please contact Administrator"));
			}
		}
	}

	private Drone getDrone(String serialNumber) {
		Drone drone = droneRepo.findBySerialNumber(serialNumber);
		logger.debug("Drone [{}]", drone);
		if (drone == null) {
			throw new DroneNotFoundException(String.format(
					configUtility.getProperty("drone.not.found.resp.desc", "Drone with serial number %s not found"),
					serialNumber));
		}
		return drone;
	}

	private Integer validateDroneForLoading(LoadMedicationItemsRequest request, Drone drone) {
		if (!isDroneLoadable(request, drone)) {
			return 401;
		} else if (isDroneLoading(drone)) {
			return 402;
		} else if (isDroneBatteryLow(drone)) {
			return 403;
		} else {
			return 101;
		}

	}

	private boolean isDroneLoading(Drone drone) {
		return drone.getState().equals(State.LOADING);
	}

	private boolean isDroneBatteryLow(Drone drone) {
		return drone.getBatteryCapacity().intValue() < configUtility.getPropertyAsInt("drone.low.battery", 25);

	}

	private boolean isDroneLoadable(LoadMedicationItemsRequest request, Drone drone) {
		Double maxWeight = drone.getMaxWeight();
		Double currentWt = drone.getMedicationList().stream().map(m -> m.getWeight()).reduce(0.0, (a, b) -> a + b);
		Double requestWt = 0.0;
		if (request != null) {
			requestWt = request.getMedicationItemList().stream().map(m -> m.getWeight()).reduce(0.0, (a, b) -> a + b);
		}
		return currentWt + requestWt <= maxWeight;
	}

}
