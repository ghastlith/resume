package ghastlith.resume.renderer.data;

import java.time.Year;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * A degree entry used in resume generation, mapped from YAML input data.
 */
@Builder
public record Degree(
    @NotBlank String institution,
    @NotBlank String degree,
    @NotNull Year from,
    @Nullable Year to
) {}
