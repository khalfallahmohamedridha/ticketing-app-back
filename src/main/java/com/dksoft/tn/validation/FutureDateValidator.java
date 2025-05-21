package com.dksoft.tn.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FutureDateValidator implements ConstraintValidator<FutureDate, String> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Let @NotBlank handle this
        }

        try {
            LocalDate date = LocalDate.parse(value, DATE_FORMATTER);
            return date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false; // Invalid date format
        }
    }
}