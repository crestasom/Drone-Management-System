package com.crestasom.dms.service.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.crestasom.dms.dto.DroneDTO;
import com.crestasom.dms.exception.ImageStoreException;
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

	@Override
	public ResponseBean register(DroneDTO droneDto) {
		// TODO Auto-generated method stub
		logger.info("Received drone for registering [{}]", droneDto);
		ResponseBean resp = new ResponseBean();
		Drone drone = droneRepo.findBySerialNumber(droneDto.getSerialNumber());
		if (drone != null) {
			resp.setRespCode(configUtility.getPropertyAsInt("register.drone.duplicate.serial.number.resp.code", 400));
			resp.setRespDesc(String.format(configUtility.getProperty("register.drone.duplicate.serial.number.resp.code",
					"Drone for serial number %s already exists"), droneDto.getSerialNumber()));

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
	@Transactional
	public ResponseBean loadMedicationItems(LoadMedicationItemsRequest request) {
		logger.info("Received LoadMedicationItemsRequest [{}]", request);
		ResponseBean resp = new ResponseBean();
		if (request.getMedicationItemList() == null || request.getMedicationItemList().size() == 0) {
			resp.setRespCode(configUtility.getPropertyAsInt("load.medication.list.empty.resp.code", 400));
			resp.setRespDesc(configUtility.getProperty("load.medication.list.empty.resp.desc",
					"Medication list is empty in request"));
		} else {
			Drone drone = droneRepo.findBySerialNumber(request.getDroneSerialNumber());
			String rootPath = configUtility.getProperty("img.store.path");
			String imgExtension = configUtility.getProperty("img.extension", ".jpg");
			if (drone != null) {
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
						.map(medicationDto -> DTOUtility.convertMedicationDtoToMedication(medicationDto, rootPath,
								imgExtension))
						.toList();
				logger.info("Converted Medication List [{}]", medicationList);
				drone.getMedicationList().addAll(medicationList);
				droneRepo.save(drone);
				storeImgToFileSystem(medicationList);
				resp.setRespCode(configUtility.getPropertyAsInt("server.success.resp.code", 200));
				resp.setRespDesc(String.format(
						configUtility.getProperty("load.medication.drone.success.resp.desc",
								"Drone with serial number %s is loaded with provided medication information"),
						request.getDroneSerialNumber()));

			} else {
				resp.setRespCode(configUtility.getPropertyAsInt("load.medication.drone.not.found.resp.code", 404));
				resp.setRespDesc(String.format(configUtility.getProperty("load.medication.drone.not.found.resp.desc",
						"Drone with serial number %s not found"), request.getDroneSerialNumber()));
			}
		}
		return resp;
	}

	private Integer validateDroneForLoading(LoadMedicationItemsRequest request, Drone drone) {
		if (!isDroneLoadable(request, drone)) {
			return configUtility.getPropertyAsInt("load.medication.drone.overloaded.resp.code", 401);
		} else if (isDroneLoading(drone)) {
			return configUtility.getPropertyAsInt("load.medication.drone.loading.resp.code", 402);
		} else if (isDroneBatteryLow(drone)) {
			return configUtility.getPropertyAsInt("load.medication.drone.battery.low.resp.code", 403);
		} else {
			return 101;
		}

	}

	private boolean isDroneLoading(Drone drone) {
		if (drone.getState().equals(State.LOADING)) {
			return true;
		}
		return false;
	}

	private boolean isDroneBatteryLow(Drone drone) {
		if (drone.getBatteryCapacity().intValue() < configUtility.getPropertyAsInt("drone.low.battery", 25)) {
			return true;
		}
		return false;
	}

	private boolean isDroneLoadable(LoadMedicationItemsRequest request, Drone drone) {

		Double maxWeight = drone.getMaxWeight();
		Double currentWt = drone.getMedicationList().stream().map(m -> m.getWeight()).reduce(0.0, (a, b) -> a + b);
		Double requestWt = request.getMedicationItemList().stream().map(m -> m.getWeight()).reduce(0.0,
				(a, b) -> a + b);
		if (currentWt + requestWt > maxWeight) {
			return false;
		}
		return true;
	}

	private void storeImgToFileSystem(List<Medication> medicationList) {
		for (Medication medication : medicationList) {
			try {
				DMSUtils.storeImgToFile(medication.getImgPath(), medication.getImgData());
			} catch (IOException e) {
				throw new ImageStoreException(e.getMessage());
			}
		}
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
