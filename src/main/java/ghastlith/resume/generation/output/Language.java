package ghastlith.resume.generation.output;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The language in which resume sections and specific keywords will be rendered.
 */
@Getter
@RequiredArgsConstructor
public enum Language {

  EN("en"),
  PT("pt");

  private final String code;

}
