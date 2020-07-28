package com.mth.webquiz.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if (value == null) {
			return false;
		}
		
		// Checa se segue o exemplo "email@site.com(.br opcional)"
		return value.matches(".+@\\w+\\.\\w+(\\.\\w+)?");
	}

}
