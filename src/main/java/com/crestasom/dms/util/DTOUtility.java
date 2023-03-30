package com.crestasom.dms.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import com.crestasom.dms.dto.DroneDTO;
import com.crestasom.dms.dto.MedicationDTO;
import com.crestasom.dms.exception.ReadImageException;
import com.crestasom.dms.model.Drone;
import com.crestasom.dms.model.Medication;
import com.crestasom.dms.model.enums.Model;
import com.crestasom.dms.model.enums.State;
import lombok.Generated;

public class DTOUtility {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static final Logger logger = LoggerFactory.getLogger(DTOUtility.class);

	@Generated
	private DTOUtility() {

	}

	public static Drone convertDroneDTOToDrone(DroneDTO dto) {
		return Drone.builder().serialNumber(dto.getSerialNumber()).model(Model.getModel(dto.getModel()))
				.maxWeight(dto.getMaxWeight()).batteryCapacity(dto.getBatteryCapacity())
				.state(State.getState(dto.getState())).build();
	}

	public static DroneDTO convertDroneToDroneDto(Drone drone) {
		return DroneDTO.builder().serialNumber(drone.getSerialNumber()).batteryCapacity(drone.getBatteryCapacity())
				.maxWeight(drone.getMaxWeight()).model(drone.getModel().toString()).state(drone.getState().toString())
				.build();
	}

	public static Medication convertMedicationDtoToMedication(MedicationDTO dto, String rootPath) {
		return Medication.builder().name(dto.getName()).weight(dto.getWeight()).code(dto.getCode())
				.imgPath(buildImgPath(dto, rootPath)).imgData(dto.getImgBase64()).build();
	}

	public static MedicationDTO convertMedicationToMedicationDto(Medication medication) {

		return MedicationDTO.builder().name(medication.getName()).code(medication.getCode())
				.weight(medication.getWeight()).imgBase64(getImage(medication.getImgPath())).build();
	}

	private static String getImage(String imgPath) {
		if (imgPath != null) {
			try {
				return DMSUtils.readImageFromFile(imgPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Exception : [{}]", e.getMessage(), e);
				throw new ReadImageException(
						"Problem while retrieving image of medication, please contact administrator");
			}
		}
		return null;
	}

	private static String buildImgPath(MedicationDTO dto, String rootPath) {
		if (ObjectUtils.isEmpty(dto.getImgBase64())) {
			return null;
		}

		String fileName = dto.getName() + "_" + sdf.format(new Date());
		return rootPath + File.separator + fileName;
	}

}
