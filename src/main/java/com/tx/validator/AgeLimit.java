package com.tx.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Repeatable(AgeLimit.List.class)
@Constraint(validatedBy = {AgeLimitValidator.class})
public @interface AgeLimit {

    String message() default "Must be within last {amount} {timeUnit}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    ChronoUnit timeUnit();

    long amount();

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        AgeLimit[] value();
    }

}
