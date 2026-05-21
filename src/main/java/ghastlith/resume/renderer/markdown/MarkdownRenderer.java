package ghastlith.resume.renderer.markdown;

import static ghastlith.resume.file.output.Format.MARKDOWN;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Component;

import ghastlith.resume.file.FileService;
import ghastlith.resume.file.output.Format;
import ghastlith.resume.renderer.data.Resume;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Manages and performs all actions regarding markdown (.md) resume file
 * creation.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MarkdownRenderer {

  private final FileService fileService;

  private static final Format FORMAT = MARKDOWN;
  private static final String IO_EXCEPTION_FORMAT = "an error happened while trying to save file {}: {}";

  /**
   * Build a new markdown resume file from inputted {@link Resume} data.
   *
   * @param resume the {@link Resume} data parsed from YAML file
   */
  public void buildMarkdown(final Resume resume) {
    final var path = fileService.getNewFile(resume, FORMAT);

    final var content = MarkdownContent.withResume(resume)
        .withTitleHeading()
        .withExperiences()
        .build();

    try {
      Files.writeString(path, content);
    } catch (IOException e) {
      log.error(IO_EXCEPTION_FORMAT, path, e);
    }
  }

}
