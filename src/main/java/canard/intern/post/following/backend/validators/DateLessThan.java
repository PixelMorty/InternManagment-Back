package canard.intern.post.following.backend.validators;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalDate;

@Target({ElementType.FIELD})
@Constraint(validatedBy = DateLessThanValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface DateLessThan{
        String message() default "date is not before 18 years ago";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
}

class DateLessThanValidator implements ConstraintValidator<DateLessThan, LocalDate> { // constraint ne sert à rien globalement

        @Override
        public boolean isValid(LocalDate localDate, ConstraintValidatorContext contraintValidatorContext){
                return localDate.isBefore(LocalDate.now().minusYears(18));
        }
}