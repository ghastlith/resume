package ghastlith.resume.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import ghastlith.resume.file.output.Format;
import ghastlith.resume.renderer.data.Resume;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import tools.jackson.dataformat.yaml.YAMLMapper;

/**
 * Manages and performs all actions regarding file system IO actions.
 */
@Component
@RequiredArgsConstructor
public class FileService {

  private final YAMLMapper yamlMapper;

  private static final Path INPUT_FOLDER = Path.of("input");
  private static final Path OUTPUT_FOLDER = Path.of("output");
  private static final Set<String> YAML_EXTENSIONS = Set.of(".yml", ".yaml");
  private static final String EXAMPLE_FILENAME = "example.yml";
  private static final String FILENAME_FORMAT = "(cv-%s) %s.%s";

  /**
   * Read YAML files from the specified input folder and parse them to a list of
   * {@link Resume} objects.
   *
   * @return A list of parsed {@link Resume} objects.
   */
  @SneakyThrows(IOException.class)
  public List<Resume> readEntries() {
    Files.createDirectories(INPUT_FOLDER);

    try (final var stream = Files.list(INPUT_FOLDER)) {
      return stream.filter(Files::isRegularFile)
          .filter(FileService::isYAMLFile)
          .filter(FileService::isNotExampleFile)
          .map(path -> yamlMapper.readValue(path.toFile(), Resume.class))
          .toList();
    }
  }

  /**
   * Create a new {@link Path} at the specified output folder with a name
   * generated from parsed input YAML data and specified format.
   *
   * @param resume the {@link Resume} data parsed from YAML file
   * @param format the {@link Format} file extension
   * @return The file path to be populated with resume data
   */
  @SneakyThrows(IOException.class)
  public Path createPath(final Resume resume, final Format format) {
    Files.createDirectories(OUTPUT_FOLDER);

    final var language = resume.language().getCode();
    final var name = resume.name().toLowerCase();
    final var extension = format.getExtension();
    final var filename = FILENAME_FORMAT.formatted(language, name, extension);

    return OUTPUT_FOLDER.resolve(filename);
  }

  private static boolean isYAMLFile(final Path path) {
    final var name = path.getFileName()
        .toString()
        .toLowerCase();

    return YAML_EXTENSIONS.stream()
        .anyMatch(name::endsWith);
  }

  private static boolean isNotExampleFile(final Path path) {
    final var name = path.getFileName()
        .toString()
        .toLowerCase();

    return !EXAMPLE_FILENAME.equals(name);
  }

}
