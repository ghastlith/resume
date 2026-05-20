package ghastlith.resume.argument;

import static ghastlith.resume.generation.output.Format.ALL;

import java.util.ArrayList;
import java.util.List;

import ghastlith.resume.generation.output.Format;
import lombok.Getter;
import picocli.CommandLine.Option;
import picocli.CommandLine.Unmatched;

/**
 * Data object parsed from user inputted application arguments.
 */
@Getter
public class Arguments {

  @Option(names = "--format")
  private Format format = ALL;

  @Unmatched
  private List<String> unmatched = new ArrayList<>();

}
