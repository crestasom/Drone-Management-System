package com.crestasom.dms.model.enums;

import com.crestasom.dms.exception.NoEnumException;

public enum Model {
	LIGHT_WEGHT, MIDDLE_WEIGHT, CRUISER_WEIGHT, HEAVY_WEIGHT;
	public static Model getModel(String model) {
		try {
			return Model.valueOf(model);
		} catch (IllegalArgumentException ex) {
			throw new NoEnumException("Drone Model Value " + model + " is not supported");
		}
	}
}
