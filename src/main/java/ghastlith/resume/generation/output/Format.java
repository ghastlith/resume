package ghastlith.resume.generation.output;

/**
 * The output format specified for the generated resume file(s).
 */
public enum Format {

  ALL,
  PDF,
  MD;

  /**
   * Check if current enum value is ALL or if it is target format for boolean
   * logic operations as ALL represents every possible format.
   *
   * @param target the target format to be checked against current value
   * @return If current value includes target format.
   */
  public boolean includes(final Format target) {
    return this == ALL || this == target;
  }

}
