package ghastlith.resume.file.output;

import static ghastlith.resume.file.output.Language.EN_US;
import static ghastlith.resume.file.output.Language.PT_BR;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LanguageTest {

  @ParameterizedTest
  @MethodSource("provideEnumsInputForFromConversion")
  void from_shouldParseLanguageCorrectlyWhenInputIsInValidFormat(final String value, final Language expected) {
    // given

    // when
    final var language = Language.from(value);

    // then
    assertThat(language).isEqualTo(expected);
  }

  private static List<Arguments> provideEnumsInputForFromConversion() {
    return List.of(
      Arguments.of("en-us", EN_US),
      Arguments.of("EN-US", EN_US),
      Arguments.of("pt-br", PT_BR),
      Arguments.of("PT-BR", PT_BR)
    );
  }

}
