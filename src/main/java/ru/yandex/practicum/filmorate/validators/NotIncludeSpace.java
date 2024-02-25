package ru.yandex.practicum.filmorate.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IncludeValidatorSpace.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotIncludeSpace {

    String message() default "Include space";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
