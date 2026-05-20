package ghastlith.resume.generation.data;

import java.time.Year;
import java.util.Optional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * A degree entry used in resume generation, mapped from YAML input data.
 */
public record Degree(
    @NotBlank String institution,
    @NotBlank String degree,
    @NotNull Year from,
    Optional<Year> to
) {}
