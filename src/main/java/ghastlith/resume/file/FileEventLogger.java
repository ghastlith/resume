package ghastlith.resume.file;

import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Component;

import ghastlith.resume.file.output.Format;
import ghastlith.resume.renderer.data.Resume;
import lombok.extern.slf4j.Slf4j;

/**
 * Logging wrapper with generic messages for all possible file handling
 * scenarios.
 */
@Component
@Slf4j
public class FileEventLogger {

  private static final String CREATED_FORMAT = "[{}] resume file was created at: {}";
  private static final String FAILURE_FORMAT = "an error happened while trying to save file {}: {}";
  private static final String ENTRIES_FORMAT = "{} YAML data files found on input folder";

  /**
   * Log generic message when resume file is created and populated correctly.
   *
   * @param resume the resume file {@link Path}
   * @param format the resume file extension
   */
  public void created(final Path path, final Format format) {
    log.info(CREATED_FORMAT, format.name(), path);
  }

  /**
   * Log generic message when resume file creation failed.
   *
   * @param resume    the resume file {@link Path}
   * @param exception the exception thrown
   */
  public void failure(final Path path, final Exception exception) {
    log.error(FAILURE_FORMAT, path, exception);
  }

  /**
   * Log generic message with found resume file count.
   *
   * @param entries the list of parsed YAML files resumes
   */
  public void count(final List<Resume> entries) {
    log.warn(ENTRIES_FORMAT, entries.size());
  }

}
