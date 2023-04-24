package com.crestasom.dms.exception;

public class InvalidCredentialException extends RuntimeException {

	private static final long serialVersionUID = -1333811782081232662L;

	public InvalidCredentialException(String msg) {
		super(msg);
	}
}
