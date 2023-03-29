package com.crestasom.dms.model.response;

import java.util.List;

import com.crestasom.dms.dto.MedicationDTO;
import com.crestasom.dms.model.ResponseBean;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class CheckMedicationResponse extends ResponseBean {

	List<MedicationDTO> medicationList;
}
