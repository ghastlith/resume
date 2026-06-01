package ghastlith.resume.renderer.data;

import java.net.URI;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

/**
 * A company regarding corporations presend on an experience entry used in
 * resume generation, mapped from YAML input data.
 */
public record Company(
    @NotBlank String name,
    @Nullable URI website
) {}
