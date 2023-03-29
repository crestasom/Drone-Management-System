package com.crestasom.dms.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.ObjectUtils;
import com.crestasom.dms.dto.DroneDTO;
import com.crestasom.dms.dto.MedicationDTO;
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

	public static Medication convertMedicationDtoToMedication(MedicationDTO dto, String rootPath, String imgExtension) {
		return Medication.builder().name(dto.getName()).weight(dto.getWeight()).code(dto.getCode()).imgPath(buildImgPath(dto, rootPath, imgExtension))
				.imgData(dto.getImgBase64()).build();
	}

	private static String buildImgPath(MedicationDTO dto, String rootPath, String imgExtension) {
		if (ObjectUtils.isEmpty(dto.getImgBase64())) {
			return null;
		}

		String fileName = dto.getName() + "_" + sdf.format(new Date());
		return rootPath + File.separator + fileName + imgExtension;
	}

}
