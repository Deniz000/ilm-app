package com.ilim.app.business.validation;

import com.ilim.app.config.auth.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, RegisterRequest> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegisterRequest request, ConstraintValidatorContext context) {
        boolean passwordsMatch = request.getPassword() != null && request.getPassword().equals(request.getMatchingPassword());
        boolean passwordValid = request.getPassword() != null && request.getPassword().length() >= 6;

        if (!passwordsMatch) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords do not match")
                    .addPropertyNode("matchingPassword") // Hangi alanda hata olduğunu belirtiyoruz
                    .addConstraintViolation();
        }

        if (!passwordValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must be at least 6 characters long")
                    .addPropertyNode("password")
                    .addConstraintViolation();
        }

        return passwordsMatch && passwordValid;
    }
}
