package ghastlith.resume.file.output;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The output format specified for the generated resume file(s).
 */
@Getter
@RequiredArgsConstructor
public enum Format {

  DOCUMENT("pdf"),
  MARKDOWN("md");

  private final String extension;

}
