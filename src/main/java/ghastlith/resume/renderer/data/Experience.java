package ghastlith.resume.renderer.data;

import java.net.URI;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * An experience entry used in resume generation, mapped from YAML input data.
 */
public record Experience(
    @NotBlank String company,
    Optional<URI> website,
    @NotBlank String role,
    @NotNull YearMonth from,
    Optional<YearMonth> to,
    @NotBlank String description,
    Optional<List<String>> tasks,
    Optional<List<String>> stack
) {}
