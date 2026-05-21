package ghastlith.resume.renderer;

import static ghastlith.resume.file.output.Format.DOCUMENT;

import org.springframework.stereotype.Component;

import ghastlith.resume.file.FileService;
import ghastlith.resume.file.output.Format;
import ghastlith.resume.renderer.data.Resume;
import lombok.RequiredArgsConstructor;

/**
 * Manages and performs all actions regarding document (.pdf) resume file
 * creation.
 */
@Component
@RequiredArgsConstructor
public class DocumentRenderer {

  private final FileService fileService;

  private static final Format FORMAT = DOCUMENT;

  /**
   * Build a new pdf resume file from inputted {@link Resume} data.
   *
   * @param resume the {@link Resume} data parsed from YAML file
   */
  public void buildDocument(final Resume resume) {
    final var path = fileService.getNewFile(resume, FORMAT);

    // TODO: implement method
    System.out.println("method not implemented yet");
  }

}
