package com.crestasom.dms.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	@Generated
	private DTOUtility() {

	}

	public static Drone convertDroneDTOToDrone(DroneDTO dto) {
		return Drone.builder().serialNumber(dto.getSerialNumber()).model(Model.getModel(dto.getModel()))
				.maxWeight(dto.getMaxWeight()).batteryCapacity(dto.getBatteryCapacity())
				.state(State.getState(dto.getState())).build();
	}

	public static Medication convertMedicationDtoToMedication(MedicationDTO dto, String rootPath) {
		return Medication.builder().name(dto.getName()).weight(dto.getWeight()).code(dto.getCode())
				.imgPath(buildImgPath(dto, rootPath)).imgData(dto.getImgBase64()).build();
	}

	public static MedicationDTO convertMedicationToMedicationDto(Medication medication) {
		String imgPath = medication.getImgPath();
		String imgDataBase64 = null;
		if (imgPath != null) {
			try {
				imgDataBase64 = DMSUtils.readImageFromFile(imgPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new ReadImageException(
						"Problem while retrieving image of medication, please contact administrator");
			}
		}
		return MedicationDTO.builder().name(medication.getName()).code(medication.getCode())
				.weight(medication.getWeight()).imgBase64(imgDataBase64).build();
	}

	private static String buildImgPath(MedicationDTO dto, String rootPath) {
		if (ObjectUtils.isEmpty(dto.getImgBase64())) {
			return null;
		}

		String fileName = dto.getName() + "_" + sdf.format(new Date());
		return rootPath + File.separator + fileName;
	}

}
