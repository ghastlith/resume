package ghastlith.resume.renderer.data;

import java.time.Year;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * A certification entry used in resume generation, mapped from YAML input data.
 */
public record Certification(
    @NotBlank String institution,
    @NotBlank String certification,
    @NotNull Year year
) {}
