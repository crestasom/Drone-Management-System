package com.crestasom.dms.service;

import com.crestasom.dms.dto.DroneDTO;
import com.crestasom.dms.model.ResponseBean;
import com.crestasom.dms.model.request.LoadMedicationItemsRequest;
import com.crestasom.dms.model.response.CheckAvailableDroneResponse;
import com.crestasom.dms.model.response.CheckBatteryPercentageResponse;
import com.crestasom.dms.model.response.CheckMedicationResponse;

public interface DroneService {

	ResponseBean register(DroneDTO droneDto);

	ResponseBean loadMedicationItems(LoadMedicationItemsRequest request);

	CheckMedicationResponse checkLoadedMedication(String serialNumber);

	CheckAvailableDroneResponse checkAvailableDroneForLoading();

	CheckBatteryPercentageResponse checkDroneBatteryLevel(String serialNumber);
}
