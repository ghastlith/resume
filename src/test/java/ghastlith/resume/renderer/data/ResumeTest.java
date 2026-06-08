package ghastlith.resume.renderer.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.net.URI;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ghastlith.resume.file.output.Language;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ResumeTest {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @ParameterizedTest
  @MethodSource("providePhonesForPhoneValidation")
  void validation_shouldParseAndValidateCorrectlyGlobalValidPhoneNumbers(final String phone) {
    // given
    final var language = Language.EN_US;
    final var name = "john constantine";
    final var role = "hellblazer";
    final var linkedin = URI.create("https://en.wikipedia.org/wiki/John_Constantine");
    final var github = URI.create("https://hellblazer.com");
    final var email = "john.constantine@hellblazer.com";

    final var resume = Resume.builder()
        .language(language)
        .name(name)
        .role(role)
        .linkedin(linkedin)
        .github(github)
        .email(email)
        .phone(phone)
        .build();

    // when
    final var violations = validator.validate(resume);

    // then
    assertThat(violations).isEmpty();
  }

  private static List<Arguments> providePhonesForPhoneValidation() {
    return List.of(
      Arguments.of("+111 (123) 456 7899"),
      Arguments.of("(123).456.7899"),
      Arguments.of("(123)-456-7899"),
      Arguments.of("123-456-7899"),
      Arguments.of("123 456 7899"),
      Arguments.of("1234567899"),
      Arguments.of("+1 (404) 248-7182"),
      Arguments.of("+55 (10) 98765-4321")
    );
  }

  @Test
  void sortedExperiences_shouldSortExperiencesBasedOnToDateThenOnFromDate() {
    // given
    final var experiences = List.of(
      Experience.builder().from(YearMonth.parse("2020-06")).to(null).build(),
      Experience.builder().from(YearMonth.parse("2010-01")).to(YearMonth.parse("2015-12")).build(),
      Experience.builder().from(YearMonth.parse("2010-01")).to(YearMonth.parse("2020-12")).build(),
      Experience.builder().from(YearMonth.parse("2025-06")).to(null).build(),
      Experience.builder().from(YearMonth.parse("2005-01")).to(YearMonth.parse("2010-12")).build(),
      Experience.builder().from(YearMonth.parse("2000-01")).to(YearMonth.parse("2020-12")).build(),
      Experience.builder().from(YearMonth.parse("2000-06")).to(YearMonth.parse("2020-06")).build(),
      Experience.builder().from(YearMonth.parse("2015-01")).to(YearMonth.parse("2025-12")).build()
    );
    final var resume = Resume.builder()
        .experiences(experiences)
        .build();

    // when
    final var sorted = resume.sortedExperiences();

    // then
    assertThat(sorted).extracting(Experience::from, Experience::to)
        .containsExactly(
          tuple(YearMonth.parse("2025-06"), null),
          tuple(YearMonth.parse("2020-06"), null),
          tuple(YearMonth.parse("2015-01"), YearMonth.parse("2025-12")),
          tuple(YearMonth.parse("2010-01"), YearMonth.parse("2020-12")),
          tuple(YearMonth.parse("2000-01"), YearMonth.parse("2020-12")),
          tuple(YearMonth.parse("2000-06"), YearMonth.parse("2020-06")),
          tuple(YearMonth.parse("2010-01"), YearMonth.parse("2015-12")),
          tuple(YearMonth.parse("2005-01"), YearMonth.parse("2010-12"))
        );
  }

  @Test
  void sortedDegrees_shouldSortDegreesBasedOnToDateThenOnFromDate() {
    // given
    final var degrees = List.of(
      Degree.builder().from(Year.parse("2020")).to(null).build(),
      Degree.builder().from(Year.parse("2010")).to(Year.parse("2015")).build(),
      Degree.builder().from(Year.parse("2010")).to(Year.parse("2020")).build(),
      Degree.builder().from(Year.parse("2025")).to(null).build(),
      Degree.builder().from(Year.parse("2005")).to(Year.parse("2010")).build(),
      Degree.builder().from(Year.parse("2000")).to(Year.parse("2020")).build(),
      Degree.builder().from(Year.parse("2015")).to(Year.parse("2025")).build()
    );
    final var resume = Resume.builder()
        .degrees(degrees)
        .build();

    // when
    final var sorted = resume.sortedDegrees();

    // then
    assertThat(sorted).extracting(Degree::from, Degree::to)
        .containsExactly(
          tuple(Year.parse("2025"), null),
          tuple(Year.parse("2020"), null),
          tuple(Year.parse("2015"), Year.parse("2025")),
          tuple(Year.parse("2010"), Year.parse("2020")),
          tuple(Year.parse("2000"), Year.parse("2020")),
          tuple(Year.parse("2010"), Year.parse("2015")),
          tuple(Year.parse("2005"), Year.parse("2010"))
        );
  }

  @Test
  void sortedCertifications_shouldSortCertificationsBasedOnYearThenOnCertificationName() {
    // given
    final var certifications = List.of(
      Certification.builder().certification("Heaven").year(Year.parse("2010")).build(),
      Certification.builder().certification("Heaven").year(Year.parse("2020")).build(),
      Certification.builder().certification("The Streets of London").year(Year.parse("2010")).build()
    );
    final var resume = Resume.builder()
        .certifications(certifications)
        .build();

    // when
    final var sorted = resume.sortedCertifications();

    // then
    assertThat(sorted).extracting(Certification::certification, Certification::year)
        .containsExactly(
          tuple("Heaven", Year.parse("2020")),
          tuple("Heaven", Year.parse("2010")),
          tuple("The Streets of London", Year.parse("2010"))
        );
  }

}
