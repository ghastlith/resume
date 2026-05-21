package ghastlith.resume.renderer.data;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import ghastlith.resume.file.output.Language;
import ghastlith.resume.renderer.data.validation.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * The information to be used in the resume generation, mapped from YAML input
 * data.
 */
public record Resume(
    Language language,
    @NotBlank String name,
    @NotBlank String role,
    URI linkedin,
    URI github,
    @Email String email,
    @Phone String phone,
    Optional<List<Experience>> experiences,
    Optional<List<Degree>> degrees,
    Optional<List<Certification>> certifications
) {}
