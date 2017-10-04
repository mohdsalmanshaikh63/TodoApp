package com.bridgelabz.restApiDemo.customAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Constraint(validatedBy=PasswordsEqualConstraint.class)
public @interface PasswordsEqualConstraint {

	public String message() default "Both password must be same";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
}
