package com.crestasom.dms.model.response;

import com.crestasom.dms.model.ResponseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CheckBatteryPercentageResponse extends ResponseBean {

	private Integer droneBatteryLevel;
	private String serialNumber;
}
