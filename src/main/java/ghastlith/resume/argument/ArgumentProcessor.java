package ghastlith.resume.argument;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

/**
 * Handles operations related to user inputted CLI arguments.
 */
@Component
@Slf4j
public class ArgumentProcessor {

  private static final String UNMATCHED_LOG_FORMAT = "unrecognized argument: {}";

  /**
   * Parse application arguments inputted by the user to internal standards
   * ignoring and displaying unmatched information.
   *
   * @param args the inputted application arguments
   * @return The internal {@code Argument} object.
   */
  public Arguments parse(final String... args) {
    final var input = new Arguments();

    parserFor(input).parseArgs(args);

    input.getUnmatched()
        .forEach(arg -> log.warn(UNMATCHED_LOG_FORMAT, arg));

    return input;
  }

  private CommandLine parserFor(final Object input) {
    return new CommandLine(input)
        .setOptionsCaseInsensitive(true)
        .setCaseInsensitiveEnumValuesAllowed(true)
        .setAbbreviatedOptionsAllowed(true);
  }

}
