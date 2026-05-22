package ghastlith.resume.renderer;

import static ghastlith.resume.file.output.Format.MARKDOWN;
import static java.util.stream.Collectors.toUnmodifiableSet;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import ghastlith.resume.file.FileEventLogger;
import ghastlith.resume.file.FileService;
import ghastlith.resume.renderer.content.MarkdownContent;
import ghastlith.resume.renderer.data.Resume;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Validates inputted YAML data and orchestrates all resume generation related
 * operations.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ResumeGenerator {

  private final Validator validator;
  private final FileService fileService;
  private final FileEventLogger fileEventLogger;

  /**
   * Generate supported resume files for every YAML data file present on designed
   * input folder.
   *
   * @throws ConstraintViolationException If resume entries have parsing errors.
   */
  public void generate() {
    final var entries = fileService.readEntries();

    fileEventLogger.count(entries);
    validate(entries);

    for (final var entry : entries) {
      generateDocument(entry);
      generateMarkdown(entry);
    }
  }

  private void generateDocument(final Resume resume) {
    // TODO: implement this function
    // final var path = fileService.createPath(resume, DOCUMENT);
    System.out.println("method not implemented");
  }

  private void generateMarkdown(final Resume resume) {
    final var path = fileService.createPath(resume, MARKDOWN);
    final var content = MarkdownContent.with(resume).build();

    try {
      Files.writeString(path, content);
      fileEventLogger.created(path, MARKDOWN);
    } catch (IOException e) {
      fileEventLogger.failure(path, e);
    }
  }

  private void validate(final List<Resume> entries) {
    final var violations = entries.stream()
        .map(validator::validate)
        .flatMap(Set::stream)
        .collect(toUnmodifiableSet());

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

}
