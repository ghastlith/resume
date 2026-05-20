package ghastlith.resume.generation;

import org.springframework.stereotype.Component;

import ghastlith.resume.argument.Arguments;

/**
 * Manages and performs all resume generation related operations.
 */
@Component
public class ResumeGenerator {

  /**
   * Generate resume files with the format specified on the CLI {@link Argument}
   * rules for all YAML data files present on input designed folder.
   *
   * @param arguments the set of rules to generate resume files
   */
  public void generate(final Arguments arguments) {
    throw new RuntimeException("method not implemented yet");
  }

}
