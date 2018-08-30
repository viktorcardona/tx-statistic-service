package com.tx.validator;

import com.tx.exception.TimeException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class AgeLimitValidator implements ConstraintValidator<AgeLimit, LocalDateTime> {

    private AgeLimit ageLimit;

    @Override
    public void initialize(AgeLimit constraintAnnotation) {
        this.ageLimit = constraintAnnotation;
    }

    @Override
    public boolean isValid(LocalDateTime age, ConstraintValidatorContext context) {

        if (isAgeValid(age)) {
            return true;
        }

        throw new TimeException("Time should be from last " + ageLimit.amount() + " " + ageLimit.timeUnit());
    }

    private boolean isAgeValid(LocalDateTime time) {
        Duration ageLimitDuration = Duration.of(this.ageLimit.amount(), this.ageLimit.timeUnit());
        long timeAgeInMillis = ChronoUnit.MILLIS.between(time.atZone(ZoneOffset.UTC).toInstant(), Instant.now());
        return timeAgeInMillis < ageLimitDuration.toMillis();
    }

}
