package com.crestasom.dms.service;

import java.util.List;

import com.crestasom.dms.dto.DroneDTO;
import com.crestasom.dms.dto.MedicationDTO;
import com.crestasom.dms.model.response.CheckAvailableDroneResponse;
import com.crestasom.dms.model.response.CheckBatteryPercentageResponse;
import com.crestasom.dms.model.response.CheckMedicationResponse;

public interface DroneService {

	void register(DroneDTO drone);

	void loadMedicationItems(String serialNumber,List<MedicationDTO> medicationItemList);

	CheckMedicationResponse checkLoadedMedication(String serialNumber);

	CheckAvailableDroneResponse checkAvailableDroneForLoading();

	CheckBatteryPercentageResponse checkDroneBatteryLevel(String serialNumber);
}
