package com.bridgelabz.restApiDemo.customAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bridgelabz.restApiDemo.entity.User;

public class PasswordsEqualConstraintValidator implements ConstraintValidator<PasswordsEqualConstraint, Object> {

	@Override
	public void initialize(PasswordsEqualConstraint arg0) {
	}
	
	@Override
	public boolean isValid(Object aUser, ConstraintValidatorContext arg1) {
		
		User user = (User)aUser;
		System.out.println("Inside constraint validator");
		return user.getPassword().equals(user.getConfirmPassword());
	}

}
