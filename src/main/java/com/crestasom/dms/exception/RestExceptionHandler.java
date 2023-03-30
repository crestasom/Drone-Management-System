package com.crestasom.dms.exception;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.crestasom.dms.model.ResponseBean;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ResponseBean handleConstraintViolationException(ConstraintViolationException ex) {
		logger.error("Exception occured!![{}]", ex.getMessage(), ex);
		ResponseBean bean = new ResponseBean();
		bean.setRespCode(HttpStatus.BAD_REQUEST.value());
		bean.setRespDesc(((ConstraintViolationException) ex).getConstraintViolations().stream()
				.map(e -> e.getMessageTemplate()).collect(Collectors.joining(",")));
		return bean;
	}

	@ExceptionHandler({ NoEnumException.class, DroneNotFoundException.class, DuplicateDroneException.class,
			NoMedicationListFoundException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ResponseBean handleNoEnumException(RuntimeException ex) {
		logger.error("Exception occured!![{}]", ex.getMessage(), ex);
		ResponseBean bean = new ResponseBean();
		bean.setRespCode(HttpStatus.BAD_REQUEST.value());
		bean.setRespDesc(ex.getMessage());
		return bean;
	}

	@ExceptionHandler({ ImageStoreException.class, ReadImageException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	ResponseBean handleImageStoreException(RuntimeException ex) {
		ResponseBean bean = new ResponseBean();
		bean.setRespCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		bean.setRespDesc(ex.getMessage());
		return bean;
	}
}