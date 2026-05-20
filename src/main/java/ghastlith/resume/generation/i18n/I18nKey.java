package ghastlith.resume.generation.i18n;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * I18n keys that refer to relative properties files to be used when providing
 * internationalized values.
 */
@Getter
@RequiredArgsConstructor
public enum I18nKey {

  SECTION_EXPERIENCE("section.experience"),
  SECTION_EDUCATION("section.education"),
  SECTION_CERTIFICATION("section.certification"),
  ROLE_CURRENT("role.current"),
  DEGREE_ONGOING("degree.ongoing");

  private final String messageKey;

}
