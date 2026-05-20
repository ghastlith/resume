package ghastlith.resume.generation.data.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.RECORD_COMPONENT;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Custom annotation to help validate phone number.
 */
@Documented
@Target({ RECORD_COMPONENT, FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {

  String message() default "invalid phone number";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}
