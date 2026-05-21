package ghastlith.resume.renderer;

import static java.util.stream.Collectors.toSet;

import java.util.List;

import org.springframework.stereotype.Component;

import ghastlith.resume.file.FileService;
import ghastlith.resume.renderer.data.Resume;
import ghastlith.resume.renderer.markdown.MarkdownRenderer;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

/**
 * Validates inputted YAML data and orchestrates all resume generation related
 * operations.
 */
@Component
@RequiredArgsConstructor
public class ResumeGenerator {

  private final Validator validator;
  private final FileService fileService;
  private final DocumentRenderer documentRenderer;
  private final MarkdownRenderer markdownRenderer;

  /**
   * Generate supported resume files for every YAML data file present on designed
   * input folder.
   *
   * @throws ConstraintViolationException If resume entries have parsing errors.
   */
  public void generate() {
    final var entries = fileService.readEntries();

    validate(entries);

    for (final var entry : entries) {
      documentRenderer.buildDocument(entry);
      markdownRenderer.buildMarkdown(entry);
    }
  }

  private void validate(final List<Resume> entries) {
    final var violations = entries.stream()
        .flatMap(entry -> validator.validate(entry).stream())
        .collect(toSet());

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

}
