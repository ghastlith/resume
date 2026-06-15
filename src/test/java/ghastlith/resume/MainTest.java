package ghastlith.resume;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import ghastlith.resume.file.FileService;
import ghastlith.resume.renderer.data.Resume;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class MainTest {

  @Autowired private Main main;
  @Autowired private ObjectMapper yamlMapper;
  @MockitoSpyBean private FileService fileService;

  private static final Path INPUT_YAML_PATH = Path.of("input/example.yml");
  private static final Path OUTPUT_MARKDOWN_PATH = Path.of("output/(cv-en) john constantine.md");
  private static final Path OUTPUT_DOCUMENT_PATH = Path.of("output/(cv-en) john constantine.pdf");

  @BeforeAll
  @AfterAll
  public static void cleanUp() throws IOException {
    Files.deleteIfExists(OUTPUT_MARKDOWN_PATH);
    Files.deleteIfExists(OUTPUT_DOCUMENT_PATH);
  }

  @Test
  void integrationTest(final CapturedOutput output) throws Exception {
    // given
    final var resume = yamlMapper.readValue(INPUT_YAML_PATH.toFile(), Resume.class);
    final var entries = singletonList(resume);
    doReturn(entries).when(fileService).readEntries();

    // when
    main.run();

    // then
    assertThat(OUTPUT_DOCUMENT_PATH).exists();
    assertThat(OUTPUT_MARKDOWN_PATH).exists();

    assertThat(output).contains("[DOCUMENT] resume file was created at: output/(cv-en) john constantine.pdf");
    assertThat(output).contains("[MARKDOWN] resume file was created at: output/(cv-en) john constantine.md");
  }

}
