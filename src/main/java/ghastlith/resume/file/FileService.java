package ghastlith.resume.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import ghastlith.resume.file.exception.InputFolderMissingException;
import ghastlith.resume.file.exception.OutputFolderMissingException;
import ghastlith.resume.file.output.Format;
import ghastlith.resume.renderer.data.Resume;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.dataformat.yaml.YAMLMapper;

/**
 * Manages and performs all actions regarding file system IO actions.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FileService {

  private final YAMLMapper yamlMapper;

  private static final Path INPUT_FOLDER = Path.of("input");
  private static final Path OUTPUT_FOLDER = Path.of("output");
  private static final Set<String> YAML_EXTENSIONS = Set.of(".yml", ".yaml");
  private static final String FILENAME_TEMPLATE = "cv - %s - %s.%s";

  /**
   * Create input and output folders to be used when retrieving and populating
   * resumes.
   *
   * @throws IOException If directories are unable to be created.
   */
  public void setup() throws IOException {
    Files.createDirectories(INPUT_FOLDER);
    Files.createDirectories(OUTPUT_FOLDER);
  }

  /**
   * Read YAML files from the specified input folder and parse them to a list of
   * {@link Resume} objects.
   *
   * @return A list of parsed {@link Resume} objects.
   * @throws InputFolderMissingException If input folder is missing.
   * @implSpec May throw unchecked exceptions due to I/O failures.
   */
  @SneakyThrows(IOException.class)
  public List<Resume> readEntries() {
    if (!Files.isDirectory(INPUT_FOLDER)) {
      throw new InputFolderMissingException();
    }

    try (final var stream = Files.list(INPUT_FOLDER)) {
      final var files = stream.filter(Files::isRegularFile)
          .filter(FileService::isYAMLFile)
          .toList();

      if (files.isEmpty()) {
        log.warn("zero YAML data files found, skipping resume generation");
      }

      return files.stream()
          .map(path -> yamlMapper.readValue(path.toFile(), Resume.class))
          .toList();
    }
  }

  /**
   * Create a new {@link Path} at the specified output filder with a name
   * generated from parsed input YAML data and specified format.
   *
   * @param resume the {@link Resume} data parsed from YAML file
   * @param format the {@link Format} file extension
   * @return The file to be populated with resume data
   */
  public Path getNewFile(final Resume resume, final Format format) {
    if (!Files.isDirectory(OUTPUT_FOLDER)) {
      throw new OutputFolderMissingException();
    }

    final var language = resume.language().getCode();
    final var name = resume.name().toLowerCase();
    final var extension = format.getExtension();
    final var filename = FILENAME_TEMPLATE.formatted(language, name, extension);

    return OUTPUT_FOLDER.resolve(filename);
  }

  private static boolean isYAMLFile(final Path path) {
    final var name = path.getFileName()
        .toString()
        .toLowerCase();

    return YAML_EXTENSIONS.stream()
        .anyMatch(name::endsWith);
  }

}
