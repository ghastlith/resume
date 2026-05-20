package ghastlith.resume.generation.output;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * The language in which resume sections and specific keywords will be rendered.
 */
public enum Language {

  EN_US,
  PT_BR;

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
