package ghastlith.resume.renderer.data;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ExperienceTest {

  @Test
  void formattedFrom_shouldFormatFromDateOnDesignatedFormat() {
    // given
    final var from = YearMonth.parse("2002-04");
    final var experience = Experience.builder()
        .from(from)
        .build();

    // when
    final var formatted = experience.formattedFrom();

    // then
    assertThat(formatted).isEqualTo("04/2002");
  }

  @Test
  void formattedTo_shouldFormatToDateOnDesignatedFormat() {
    // given
    final var to = YearMonth.parse("2006-07");
    final var experience = Experience.builder()
        .to(to)
        .build();

    // when
    final var formatted = experience.formattedTo();

    // then
    assertThat(formatted).isEqualTo("07/2006");
  }

  @Test
  void formattedTo_shouldReturnNullWhenNoToDateProvided() {
    // given
    final var experience = Experience.builder()
        .to(null)
        .build();

    // when
    final var formatted = experience.formattedTo();

    // then
    assertThat(formatted).isEqualTo(null);
  }

  @Test
  void formattedTasks_shouldReturnListOfTasksWithItemsOnDesignatedFormat() {
    // given
    final var tasks = List.of(
      "Coordinated operations against mystical threats beyond the paygrade of Justice League",
      "Mediated interpersonal conflicts between emotional ghosts and ancient swamp monsters",
      "Developed defensive strategies against universe-ending magical anomalies"
    );
    final var experience = Experience.builder()
        .tasks(tasks)
        .build();

    // when
    final var formatted = experience.formattedTasks();

    // then
    assertThat(formatted).containsExactly(
      "Coordinated operations against mystical threats beyond the paygrade of Justice League;",
      "Mediated interpersonal conflicts between emotional ghosts and ancient swamp monsters;",
      "Developed defensive strategies against universe-ending magical anomalies;"
    );
  }

  @Test
  void collectedSkills_shouldReturnCollectedStringWhenSkillsProvided() {
    // given
    final var skills = List.of(
      "Smoking",
      "Drinking",
      "Demonology"
    );
    final var experience = Experience.builder()
        .skills(skills)
        .build();

    // when
    final var current = experience.skills();
    final var collected = experience.collectedSkills();

    // then
    assertThat(current).isNotEmpty();
    assertThat(collected).isEqualTo("Demonology, Drinking, Smoking");
  }

  @Test
  void collectedSkills_shouldReturnEmptyStringWhenNoSkillsProvided() {
    // given
    final List<String> skills = emptyList();
    final var experience = Experience.builder()
        .skills(skills)
        .build();

    // when
    final var current = experience.skills();
    final var collected = experience.collectedSkills();

    // then
    assertThat(current).isEmpty();
    assertThat(collected).isEmpty();
  }

}
