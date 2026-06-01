package ghastlith.resume.renderer.data;

import static com.fasterxml.jackson.annotation.Nulls.AS_EMPTY;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Optional.ofNullable;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * An experience entry used in resume generation, mapped from YAML input data.
 */
public record Experience(
    @NotNull Company company,
    @Nullable Company client,
    @NotBlank String role,
    @NotNull YearMonth from,
    @Nullable YearMonth to,
    @JsonSetter(nulls = AS_EMPTY)
    String description,
    @JsonSetter(nulls = AS_EMPTY)
    List<String> tasks,
    @JsonSetter(nulls = AS_EMPTY)
    List<String> stack
) {

  private static final String DATE_PATTERN = "MM/yyyy";
  private static final DateTimeFormatter DATE_FORMATTER = ofPattern(DATE_PATTERN);
  private static final String PERIOD = ".";
  private static final String SEMICOLON = ";";
  private static final String MESSAGE_FORMAT = "%s%s";

  public String formattedFrom() {
    return DATE_FORMATTER.format(from());
  }

  @Nullable
  public String formattedTo() {
    return ofNullable(to())
        .map(DATE_FORMATTER::format)
        .orElse(null);
  }

  public String formattedDescription() {
    final var message = description();
    final var isFormatted = message.isBlank() || message.endsWith(PERIOD);

    if (isFormatted) {
      return message;
    }

    return MESSAGE_FORMAT.formatted(message, PERIOD);
  }

  public List<String> formattedTasks() {
    return tasks()
        .stream()
        .map(this::formatTask)
        .toList();
  }

  private String formatTask(final String task) {
    if (task.endsWith(SEMICOLON)) {
      return task;
    }

    return MESSAGE_FORMAT.formatted(task, SEMICOLON);
  }

}
