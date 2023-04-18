package com.crestasom.dms.validator;

import com.crestasom.dms.model.enums.Model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ModelValidator implements ConstraintValidator<ValidModel, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		for (Model model : Model.values()) {
			if (model.toString().equals(value)) {
				return true;
			}
		}
		return false;
	}

}
