package ghastlith.resume.renderer.data;

import java.net.URI;

import jakarta.validation.constraints.NotBlank;

/**
 * Company data from the experience entry used in resume generation, mapped from
 * YAML input data.
 */
public record Company(
    @NotBlank String name,
    URI website
) {}
