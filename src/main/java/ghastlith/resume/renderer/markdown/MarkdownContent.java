package ghastlith.resume.renderer.markdown;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;

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

  private static final String LINE_BREAK = "\n";

  private static final String TITLE_FORMAT = "## %s - %s";
  private static final String COMPANY_FORMAT = "\n### %s\n";
  private static final String TASK_FORMAT = "• %s;";

  /**
   * Initialize {@link MarkdownContent} with parsed resume data.
   */
  public static MarkdownContent withResume(final Resume resume) {
    return new MarkdownContent(resume);
  }

  /**
   * Concatenate main header to current markdown content.
   */
  public MarkdownContent withTitleHeading() {
    final var name = resume.name().toLowerCase();
    final var language = resume.language().getCode();
    final var heading = TITLE_FORMAT.formatted(name, language);

    append(heading);
    return this;
  }

  /**
   * Concatenate company name and tasks from every experience to current markdown
   * content.
   */
  public MarkdownContent withExperiences() {
    resume.experiences()
        .orElse(emptyList())
        .stream()
        .sorted(comparing(Experience::from).reversed())
        .toList()
        .forEach(this::appendExperience);

    return this;
  }

  private void appendExperience(final Experience experience) {
    final var company = COMPANY_FORMAT.formatted(experience.company());
    append(company);
    append(experience.description());

    experience.tasks()
        .orElse(emptyList())
        .stream()
        .map(TASK_FORMAT::formatted)
        .forEach(this::append);
  }

  /**
   * Build content string from current {@link MarkdownContent} appended text data.
   *
   * @return The bundled data to be written on a markdown file.
   */
  public String build() {
    return builder.toString();
  }

  private void append(final String content) {
    builder.append(content).append(LINE_BREAK);
  }

}
