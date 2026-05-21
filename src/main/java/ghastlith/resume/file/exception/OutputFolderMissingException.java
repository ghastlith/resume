package ghastlith.resume.file.exception;

/**
 * {@link OutputFolderMissingException} is thrown when root directory does not
 * contain output folder.
 */
public class OutputFolderMissingException extends RuntimeException {

  public OutputFolderMissingException() {
    super("output folder is missing from root directory");
  }

}
