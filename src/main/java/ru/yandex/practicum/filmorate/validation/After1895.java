package ru.yandex.practicum.filmorate.validation;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalDate;
import java.time.temporal.Temporal;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AfterDateValidator.class)
@Documented
public @interface After1895 {
    String message() default "Date can't be earlier than 28.12.1895";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
class AfterDateValidator implements ConstraintValidator<After1895, Temporal> {
    @Override
    public void initialize(After1895 annotation) {
    }

    @Override
    public boolean isValid(Temporal value, ConstraintValidatorContext context) {
        return LocalDate.from(value).isAfter(LocalDate.of(1895, 12, 28));
    }
}
