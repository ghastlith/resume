package ghastlith.resume.renderer.data.validation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Custom regex pattern validation to check if phone number is following
 * expected format.
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

  private static final String REGEX = "^(\\+\\d{1,3}( )?)?((\\(\\d{2,3}\\))|\\d{3})[- .]?\\d{3,5}[- .]?\\d{4}$";
  private static final Pattern PATTERN = Pattern.compile(REGEX);

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    if (value == null || value.isBlank()) {
      return false;
    }

    return PATTERN.matcher(value).matches();
  }

}
