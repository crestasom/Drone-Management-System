package com.crestasom.dms.model.response;

import java.util.List;

import com.crestasom.dms.dto.MedicationDTO;
import com.crestasom.dms.model.ResponseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CheckMedicationResponse extends ResponseBean {

	List<MedicationDTO> medicationList;
}
