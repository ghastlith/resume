package ghastlith.resume.argument;

import static ghastlith.resume.argument.OutputFormat.ALL;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import picocli.CommandLine.Option;
import picocli.CommandLine.Unmatched;

/**
 * Data object parsed from user inputted application arguments.
 */
@Getter
public class Arguments {

  @Option(names = "--format")
  private OutputFormat format = ALL;

  @Unmatched
  private List<String> unmatched = new ArrayList<>();

}
