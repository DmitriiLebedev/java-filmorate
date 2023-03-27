package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.Temporal;

class AfterDateValidator implements ConstraintValidator<After1895, Temporal> {
    @Override
    public void initialize(After1895 annotation) {
    }

    @Override
    public boolean isValid(Temporal value, ConstraintValidatorContext context) {
        return LocalDate.from(value).isAfter(LocalDate.of(1895, 12, 28));
    }
}
