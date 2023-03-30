package com.crestasom.dms.model.response;

import java.util.List;

import com.crestasom.dms.dto.DroneDTO;
import com.crestasom.dms.model.ResponseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CheckAvailableDroneResponse extends ResponseBean {

	private List<DroneDTO> droneList;
}
