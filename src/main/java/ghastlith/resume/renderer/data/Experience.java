package ghastlith.resume.renderer.data;

import static com.fasterxml.jackson.annotation.Nulls.AS_EMPTY;

import java.net.URI;
import java.time.YearMonth;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * An experience entry used in resume generation, mapped from YAML input data.
 */
public record Experience(
    @NotBlank String company,
    URI website,
    @NotBlank String role,
    @NotNull YearMonth from,
    YearMonth to,
    @NotBlank String description,
    @JsonSetter(nulls = AS_EMPTY)
    List<String> tasks,
    @JsonSetter(nulls = AS_EMPTY)
    List<String> stack
) {}
