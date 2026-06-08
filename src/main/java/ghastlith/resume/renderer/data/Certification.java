package ghastlith.resume.renderer.data;

import java.time.Year;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * A certification entry used in resume generation, mapped from YAML input data.
 */
@Builder
public record Certification(
    @NotBlank String institution,
    @NotBlank String certification,
    @NotNull Year year
) {}
