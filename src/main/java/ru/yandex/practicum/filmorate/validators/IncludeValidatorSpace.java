package ru.yandex.practicum.filmorate.validators;

import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class IncludeValidatorSpace implements ConstraintValidator<NotIncludeSpace, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.contains(" ");
    }
}
