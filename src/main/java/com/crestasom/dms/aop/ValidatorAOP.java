package com.crestasom.dms.aop;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import com.crestasom.dms.dto.BaseDTO;
import com.crestasom.dms.exception.InvalidBeanException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Aspect
@Configuration
public class ValidatorAOP {

	private final Validator validator;

	/**
	 * This method is added for performing bean validation for all the DTO objects
	 * passed during insertion.
	 */
	@Around("within(com.crestasom.dms.*.controller..* ||com.crestasom.dms.controller.* )")
	public Object validateBean(ProceedingJoinPoint pjp) throws Throwable {
		Object obj = pjp.getArgs()[0];
		if (obj instanceof List) {
			List<?> objList = (List<?>) obj;
			if (objList != null && objList.size() > 0 && objList.get(0) instanceof BaseDTO) {
				objList.stream().forEach(o -> checkValidBean(o));
			}

		} else {
			if (obj instanceof BaseDTO) {
				checkValidBean(obj);
			}
		}
		Object output = pjp.proceed();
		return output;
	}

	private void checkValidBean(Object obj) {
		Set<ConstraintViolation<Object>> violations = validator.validate(obj);
		if (!violations.isEmpty()) {
			throw new InvalidBeanException(
					violations.stream().map(v -> v.getMessage()).collect(Collectors.joining(",")));
		}
	}
}
