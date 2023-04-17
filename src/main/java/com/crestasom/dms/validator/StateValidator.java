package com.crestasom.dms.validator;

import com.crestasom.dms.model.enums.State;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidator implements ConstraintValidator<ValidState, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		for (State state : State.values()) {
			if (state.toString().equals(value)) {
				return true;
			}
		}
		return false;
	}

}
