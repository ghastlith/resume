package ghastlith.resume.renderer.content;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import ghastlith.resume.renderer.data.Experience;
import ghastlith.resume.renderer.data.Resume;

/**
 * The Markdown file content builder used when generating markdown files from
 * parsed resume data.
 */
public class MarkdownContent extends Content {

  private final StringBuilder builder = new StringBuilder();

  private static final String NL = System.lineSeparator();
  private static final String TITLE_FORMAT = "## %s - %s" + NL;
  private static final String COMPANY_FORMAT = NL + "### %s" + NL + NL;
  private static final String DESCRIPTION_FORMAT = "%s" + NL + NL;
  private static final String TASK_FORMAT = "• %s" + NL;

  public MarkdownContent(final Path path, final Resume resume) {
    super(path, resume);
  }

  /**
   * Build markdown content string from current appended builder text parsed data
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
    final var company = COMPANY_FORMAT.formatted(experience.company().name());
    builder.append(company);

    final var description = DESCRIPTION_FORMAT.formatted(experience.formattedDescription());
    if (!description.isBlank()) {
      builder.append(description);
    }

    experience.formattedTasks()
        .stream()
        .map(TASK_FORMAT::formatted)
        .forEach(builder::append);
  }

}
