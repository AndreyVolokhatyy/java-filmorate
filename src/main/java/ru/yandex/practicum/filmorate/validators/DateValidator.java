package ru.yandex.practicum.filmorate.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator implements ConstraintValidator<MinDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return value != null && value.isAfter(LocalDate.parse("1895-12-28", formatter));
    }
}
