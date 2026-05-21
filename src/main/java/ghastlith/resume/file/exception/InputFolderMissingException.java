package ghastlith.resume.file.exception;

/**
 * {@link InputFolderMissingException} is thrown when root directory does not
 * contain input folder.
 */
public class InputFolderMissingException extends RuntimeException {

  public InputFolderMissingException() {
    super("input folder is missing from root directory");
  }

}
