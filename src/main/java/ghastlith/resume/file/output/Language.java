package ghastlith.resume.file.output;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The language of the resume and in which specific keywords will be rendered.
 */
@Getter
@RequiredArgsConstructor
public enum Language {

  EN_US("en", Locale.US),
  PT_BR("pt", Locale.of("pt", "BR"));

  private final String code;
  private final Locale locale;

  private static final String DASH = "-";
  private static final String UNDERSCORE = "_";

  @JsonCreator
  public static Language from(final String value) {
    final var language = value.trim()
        .toUpperCase()
        .replace(DASH, UNDERSCORE);

    return Language.valueOf(language);
  }

}
