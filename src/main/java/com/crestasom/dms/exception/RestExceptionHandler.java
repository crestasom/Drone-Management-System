package com.crestasom.dms.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.crestasom.dms.model.ResponseBean;
import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler({ NoEnumException.class, DroneNotFoundException.class, DuplicateDroneException.class,
			NoMedicationListFoundException.class, InvalidBeanException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	ResponseBean handleBadRequestException(RuntimeException ex) {
		logger.error("Exception occured!![{}]", ex.getMessage(), ex);
		ResponseBean bean = new ResponseBean();
		bean.setRespCode(HttpStatus.BAD_REQUEST.value());
		bean.setRespDesc(ex.getMessage());
		return bean;
	}

	@ExceptionHandler({ InvalidCredentialException.class })
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	ResponseBean handleInvalidCredentialException(RuntimeException ex) {
		logger.error("Exception occured!![{}]", ex.getMessage(), ex);
		ResponseBean bean = new ResponseBean();
		bean.setRespCode(HttpStatus.FORBIDDEN.value());
		bean.setRespDesc(ex.getMessage());
		return bean;
	}

	@ExceptionHandler({ ImageStoreException.class, ReadImageException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	ResponseBean handleImageStoreException(RuntimeException ex) {
		logger.error("Exception occured!![{}]", ex.getMessage(), ex);
		ResponseBean bean = new ResponseBean();
		bean.setRespCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		bean.setRespDesc(ex.getMessage());
		return bean;
	}
}