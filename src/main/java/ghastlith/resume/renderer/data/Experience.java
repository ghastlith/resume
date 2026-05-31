package ghastlith.resume.renderer.data;

import static com.fasterxml.jackson.annotation.Nulls.AS_EMPTY;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * An experience entry used in resume generation, mapped from YAML input data.
 */
public record Experience(
    @NotNull Company company,
    Company client,
    @NotBlank String role,
    @NotNull YearMonth from,
    YearMonth to,
    @JsonSetter(nulls = AS_EMPTY)
    String description,
    @JsonSetter(nulls = AS_EMPTY)
    List<String> tasks,
    @JsonSetter(nulls = AS_EMPTY)
    List<String> stack
) {

  private static final String DATE_PATTERN = "MM/yyyy";
  private static final DateTimeFormatter FORMATTER = ofPattern(DATE_PATTERN);
  private static final String PERIOD = ".";
  private static final String SEMICOLON = ";";
  private static final String MESSAGE_FORMAT = "%s%s";

  public String formattedFrom() {
    return FORMATTER.format(from());
  }

  public String formattedTo() {
    final var date = to();
    return date == null ? null : FORMATTER.format(date);
  }

  public String formattedDescription() {
    final var message = description();
    final var isFormatted = message.isBlank() || message.endsWith(PERIOD);
    return isFormatted ? message : MESSAGE_FORMAT.formatted(message, PERIOD);
  }

  public List<String> formattedTasks() {
    return tasks()
        .stream()
        .map(this::formatTask)
        .toList();
  }

  private String formatTask(final String task) {
    final var isFormatted = task.endsWith(SEMICOLON);
    return isFormatted ? task : MESSAGE_FORMAT.formatted(task, SEMICOLON);
  }

}
