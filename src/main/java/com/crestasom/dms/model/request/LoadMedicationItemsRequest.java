package com.crestasom.dms.model.request;

import java.util.List;

import com.crestasom.dms.dto.MedicationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoadMedicationItemsRequest {

	private String droneSerialNumber;
	private List<MedicationDTO> medicationItemList;
}
