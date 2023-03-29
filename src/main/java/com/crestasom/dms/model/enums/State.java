package com.crestasom.dms.model.enums;

import com.crestasom.dms.exception.NoEnumException;

public enum State {
	IDLE, LOADING, LOADED, DELIVEREING, DELIVERED, RETURING;
	public static State getState(String state) {
		try {
			return State.valueOf(state);
		} catch (IllegalArgumentException ex) {
			throw new NoEnumException("Drone State Value " + state + " is not supported");
		}
	}
}
