package ru.yandex.practicum.filmorate.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

public class DurationValidator implements ConstraintValidator<MinDuration, Duration> {

    @Override
    public boolean isValid(Duration value, ConstraintValidatorContext context) {
        return value != null && value.compareTo(Duration.ofMinutes(30)) > 0;
    }
}
