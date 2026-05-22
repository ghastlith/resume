package ghastlith.resume.renderer;

import static ghastlith.resume.file.output.Format.MARKDOWN;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import ghastlith.resume.file.FileService;
import ghastlith.resume.renderer.content.MarkdownContent;
import ghastlith.resume.renderer.data.Resume;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Validates inputted YAML data and orchestrates all resume generation related
 * operations.
 */
@Component
@Validated
@RequiredArgsConstructor
@Slf4j
public class ResumeGenerator {

  private final FileService fileService;

  private static final String CREATED_LOG_FORMAT = "[{}] resume file was created at: {}";
  private static final String FAILURE_LOG_FORMAT = "an error happened while trying to save file {}: {}";
  private static final String ENTRIES_LOG_FORMAT = "{} YAML data files found on input folder";

  /**
   * Generate supported resume files for every YAML data file present on designed
   * input folder.
   *
   * @throws ConstraintViolationException If resume entries have parsing errors.
   */
  public void generate(@Valid final List<Resume> entries) {
    log.warn(ENTRIES_LOG_FORMAT, entries.size());

    for (final var entry : entries) {
      generateDocument(entry);
      generateMarkdown(entry);
    }
  }

  private void generateDocument(final Resume resume) {
    // final var path = fileService.createPath(resume, DOCUMENT);
    // final var content = DocumentContent.with(resume).build();

    // try {
    // TODO: save DOCUMENT here.
    // log.info(CREATED_LOG_FORMAT, DOCUMENT.name(), path);
    // } catch (IOException e) {
    // log.error(FAILURE_LOG_FORMAT, path, e);
    // }
    System.out.println("method not implemented");
  }

  private void generateMarkdown(final Resume resume) {
    final var path = fileService.createPath(resume, MARKDOWN);
    final var content = MarkdownContent.with(resume).build();

    try {
      Files.writeString(path, content);
      log.info(CREATED_LOG_FORMAT, MARKDOWN.name(), path);
    } catch (IOException e) {
      log.error(FAILURE_LOG_FORMAT, path, e);
    }
  }

}
