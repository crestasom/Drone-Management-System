package com.crestasom.dms.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

import lombok.Getter;

@Getter
public class InvalidBeanException extends RuntimeException {

	private static final long serialVersionUID = 3466701134621355587L;
	private List<ObjectError> errorList;

	public InvalidBeanException(String message) {
		super(message);
	}

	public InvalidBeanException(String message, List<ObjectError> errList) {
		super(message);
		errorList = errList;
	}

}
