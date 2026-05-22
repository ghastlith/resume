package ghastlith.resume.renderer.content;

import ghastlith.resume.renderer.data.Experience;
import ghastlith.resume.renderer.data.Resume;
import lombok.RequiredArgsConstructor;

/**
 * The content string builder to be used when populating a markdown file with
 * resume data.
 */
@RequiredArgsConstructor
public class MarkdownContent {

  private final Resume resume;
  private final StringBuilder builder = new StringBuilder();

  private static final String TITLE_FORMAT = "## %s - %s\n";
  private static final String COMPANY_FORMAT = "\n### %s\n\n";
  private static final String DESCRIPTION_FORMAT = "%s.\n";
  private static final String TASK_FORMAT = "• %s;\n";

  /**
   * Initialize {@link MarkdownContent} with parsed resume data.
   *
   * @param resume the parsed resume data
   */
  public static MarkdownContent with(final Resume resume) {
    return new MarkdownContent(resume);
  }

  /**
   * Build content string from current {@link MarkdownContent} appended text data.
   *
   * @return The bundled data to be written on a markdown file.
   */
  public String build() {
    appendNameTitle();

    resume.sortedExperiences().forEach(this::appendExperience);

    return builder.toString();
  }

  private void appendNameTitle() {
    final var name = resume.name().toLowerCase();
    final var language = resume.language().getCode();
    final var heading = TITLE_FORMAT.formatted(name, language);
    builder.append(heading);
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
  }

}
