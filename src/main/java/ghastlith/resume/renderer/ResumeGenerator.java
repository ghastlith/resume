package ghastlith.resume.renderer;

import static ghastlith.resume.file.output.Format.DOCUMENT;
import static ghastlith.resume.file.output.Format.MARKDOWN;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.thymeleaf.TemplateEngine;

import ghastlith.resume.file.FileService;
import ghastlith.resume.file.output.Format;
import ghastlith.resume.renderer.content.Content;
import ghastlith.resume.renderer.content.DocumentContent;
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
  private final TemplateEngine templateEngine;

  private static final String CREATED_LOG_FORMAT = "[{}] resume file was created at: {}";
  private static final String FAILURE_LOG_FORMAT = "an error happened while trying to save file {}: {}";
  private static final String ENTRIES_LOG_FORMAT = "{} YAML data file(s) found on input folder";
  private static final String PATH_SEPARATOR = "\\";
  private static final String UNIX_SEPARATOR = "/";

  /**
   * Generate supported resume files for every YAML data file present on designed
   * input folder.
   *
   * @throws ConstraintViolationException If resume entries have parsing errors.
   */
  public void generate(final List<@Valid Resume> entries) {
    log.warn(ENTRIES_LOG_FORMAT, entries.size());

    for (final var entry : entries) {
      generateDocument(entry, DOCUMENT);
      generateMarkdown(entry, MARKDOWN);
    }
  }

  private void generateDocument(final Resume resume, final Format format) {
    final var path = fileService.createPath(resume, format);
    final var content = new DocumentContent(path, resume, templateEngine);
    generateFile(content, format, path);
  }

  private void generateMarkdown(final Resume resume, final Format format) {
    final var path = fileService.createPath(resume, format);
    final var content = new MarkdownContent(path, resume);
    generateFile(content, format, path);
  }

  private void generateFile(final Content content, final Format format, final Path path) {
    final var normalized = path.toString().replace(PATH_SEPARATOR, UNIX_SEPARATOR);

    try {
      content.writeToFile();
      log.info(CREATED_LOG_FORMAT, format.name(), normalized);
    } catch (IOException e) {
      log.error(FAILURE_LOG_FORMAT, path, e);
    }
  }

}
