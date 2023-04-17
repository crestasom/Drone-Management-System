package com.crestasom.dms.aop;

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
		if (obj instanceof BaseDTO) {
			Set<ConstraintViolation<Object>> violations = validator.validate(obj);
			if (!violations.isEmpty()) {
				throw new InvalidBeanException(
						violations.stream().map(v -> v.getMessage()).collect(Collectors.joining(",")));
			}
		}
		Object output = pjp.proceed();
		return output;
	}
}
