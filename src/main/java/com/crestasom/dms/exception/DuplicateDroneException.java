package com.crestasom.dms.exception;

public class DuplicateDroneException extends RuntimeException {
	private static final long serialVersionUID = 3518238646657849910L;
	
	public DuplicateDroneException(String msg) {
		super(msg);
	}

}
