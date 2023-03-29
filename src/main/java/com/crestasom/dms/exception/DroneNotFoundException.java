package com.crestasom.dms.exception;

public class DroneNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -87300223318270341L;

	public DroneNotFoundException(String msg) {
		super(msg);
	}

}
