package ghastlith.resume.file;

import static ghastlith.resume.file.output.Format.DOCUMENT;
import static ghastlith.resume.file.output.Format.MARKDOWN;
import static ghastlith.resume.file.output.Language.EN_US;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ghastlith.resume.renderer.data.Resume;
import tools.jackson.dataformat.yaml.YAMLMapper;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

  @Mock private YAMLMapper mockYamlMapper;
  @TempDir Path directory;

  private Path inputFolder;
  private Path outputFolder;
  private FileService fileService;

  @BeforeEach
  void setUp() {
    inputFolder = directory.resolve("input");
    outputFolder = directory.resolve("output");
    fileService = new FileService(mockYamlMapper, inputFolder, outputFolder);
  }

  @Test
  void readEntries_shouldCreateInputFolderIfAbsent() {
    // given

    // when
    fileService.readEntries();

    // then
    assertThat(Files.exists(inputFolder)).isTrue();
  }

  @Test
  void readEntries_shouldIgnoreExampleFileIfPresent() throws IOException {
    // given
    Files.createDirectories(inputFolder);
    Files.createFile(inputFolder.resolve("example.yml"));

    // when
    final var entries = fileService.readEntries();

    // then
    assertThat(entries).isEmpty();
    verifyNoInteractions(mockYamlMapper);
  }

  @Test
  void readEntries_shouldReadAllAndOnlyYAMLFilesFromInputRepository() throws IOException {
    // given
    Files.createDirectories(inputFolder);
    Files.createFile(inputFolder.resolve("constantine.yml"));
    Files.createFile(inputFolder.resolve("zatanna.yaml"));
    Files.createFile(inputFolder.resolve("ARTEMIS.YAML"));
    Files.createFile(inputFolder.resolve("batman.png"));
    Files.createFile(inputFolder.resolve("dr-fate.txt"));

    // when
    final var entries = fileService.readEntries();

    // then
    assertThat(entries).hasSize(3);
  }

  @Test
  void createPath_shouldCreateOutputFolderIfAbsent() {
    // given
    final var resume = Resume.builder()
        .language(EN_US)
        .name("john constantine")
        .build();
    final var format = DOCUMENT;

    // when
    fileService.createPath(resume, format);

    // then
    assertThat(Files.exists(outputFolder)).isTrue();
  }

  @Test
  void createPath_shouldCreatePathWithDesignedOutputFilenameFormat() {
    // given
    final var resume = Resume.builder()
        .language(EN_US)
        .name("john constantine")
        .build();

    // when
    final var pdf = fileService.createPath(resume, DOCUMENT);
    final var markdown = fileService.createPath(resume, MARKDOWN);

    // then
    assertThat(pdf.getFileName().toString()).isEqualTo("(cv-en) john constantine.pdf");
    assertThat(markdown.getFileName().toString()).isEqualTo("(cv-en) john constantine.md");
  }

}
