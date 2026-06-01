package ghastlith.resume.renderer.data;

import static com.fasterxml.jackson.annotation.Nulls.AS_EMPTY;
import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;
import static java.util.Comparator.reverseOrder;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;

import ghastlith.resume.file.output.Language;
import ghastlith.resume.renderer.data.validation.Phone;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * The information to be used in the resume generation, mapped from YAML input
 * data.
 */
public record Resume(
    @NotNull Language language,
    @NotBlank String name,
    @NotBlank String role,
    @NotNull URI linkedin,
    @NotNull URI github,
    @Email String email,
    @Phone String phone,
    @JsonSetter(nulls = AS_EMPTY)
    List<@Valid Experience> experiences,
    @JsonSetter(nulls = AS_EMPTY)
    List<@Valid Degree> degrees,
    @JsonSetter(nulls = AS_EMPTY)
    List<@Valid Certification> certifications
) {

  public List<Experience> sortedExperiences() {
    final var comparator = comparing(Experience::to, nullsLast(naturalOrder()))
        .reversed()
        .thenComparing(Experience::from, reverseOrder());

    return experiences()
        .stream()
        .sorted(comparator)
        .toList();
  }

  public List<Degree> sortedDegrees() {
    final var comparator = comparing(Degree::to, nullsLast(naturalOrder()))
        .reversed()
        .thenComparing(Degree::from, reverseOrder());

    return degrees()
        .stream()
        .sorted(comparator)
        .toList();
  }

  public List<Certification> sortedCertifications() {
    final var comparator = comparing(Certification::year)
        .reversed()
        .thenComparing(Certification::certification);

    return certifications()
        .stream()
        .sorted(comparator)
        .toList();
  }

}
