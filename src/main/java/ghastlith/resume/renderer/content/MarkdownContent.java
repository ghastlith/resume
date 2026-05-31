package ghastlith.resume.renderer.content;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import ghastlith.resume.renderer.data.Experience;
import ghastlith.resume.renderer.data.Resume;

/**
 * The content string builder to be used when populating a markdown file with
 * resume data.
 */
public class MarkdownContent extends Content {

  private final StringBuilder builder = new StringBuilder();

  private static final String TITLE_FORMAT = "## %s - %s\n";
  private static final String COMPANY_FORMAT = "\n### %s\n\n";
  private static final String DESCRIPTION_FORMAT = "%s.\n\n";
  private static final String TASK_FORMAT = "• %s;\n";
  private static final String STACK_FORMAT = "\nStack: %s.\n";
  private static final String STACK_DELIMITER = ", ";

  public MarkdownContent(final Path path, final Resume resume) {
    super(path, resume);
  }

  /**
   * Build content string from current {@link MarkdownContent} appended text data
   * and write it on the generated markdown file from provided path.
   *
   * @throws IOException If unable to write string contents to markdown file.
   */
  public void writeToFile() throws IOException {
    appendNameTitle();
    appendExperiences();

    final var content = builder.toString();
    Files.writeString(getPath(), content);
  }

  private void appendNameTitle() {
    final var resume = getResume();
    final var name = resume.name().toLowerCase();
    final var language = resume.language().getCode();
    final var heading = TITLE_FORMAT.formatted(name, language);
    builder.append(heading);
  }

  private void appendExperiences() {
    final var experiences = getResume().sortedExperiences();

    for (final var experience : experiences) {
      appendExperience(experience);
    }
  }

  private void appendExperience(final Experience experience) {
    final var company = COMPANY_FORMAT.formatted(experience.company());
    builder.append(company);

    final var description = DESCRIPTION_FORMAT.formatted(experience.description());
    builder.append(description);

    experience.tasks()
        .stream()
        .map(TASK_FORMAT::formatted)
        .forEach(builder::append);

    experience.stack()
        .stream()
        .sorted()
        .collect(joining(STACK_DELIMITER))
        .transform(Optional::ofNullable)
        .filter(joined -> !joined.isEmpty())
        .map(STACK_FORMAT::formatted)
        .ifPresent(builder::append);
  }

}
