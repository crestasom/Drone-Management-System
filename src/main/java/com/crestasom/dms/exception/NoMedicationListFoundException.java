package com.crestasom.dms.exception;

public class NoMedicationListFoundException extends RuntimeException {

	private static final long serialVersionUID = 3767323778725715278L;

	public NoMedicationListFoundException(String msg) {
		super(msg);
	}
}
