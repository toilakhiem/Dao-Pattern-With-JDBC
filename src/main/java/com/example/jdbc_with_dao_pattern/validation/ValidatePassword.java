package com.example.jdbc_with_dao_pattern.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.regex.Pattern;

import static com.example.jdbc_with_dao_pattern.constant.JdbcWithDaoPatternConstant.Validation.PASSWORD_REGEX;

@Constraint(validatedBy = ValidatePassword.UserNameValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
public @interface ValidatePassword {
    String message() default "Invalid Password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class UserNameValidator implements ConstraintValidator<ValidatePassword, String> {
        @Override
        public void initialize(ValidatePassword constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
            return Pattern.matches(PASSWORD_REGEX,password);
        }
    }
}
