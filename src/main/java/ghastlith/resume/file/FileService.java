package ghastlith.resume.file;

import static java.util.stream.Collectors.toUnmodifiableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import ghastlith.resume.file.exception.InputFolderMissingException;
import ghastlith.resume.generation.data.Resume;
import lombok.RequiredArgsConstructor;
import tools.jackson.dataformat.yaml.YAMLMapper;

/**
 * Manages and performs all actions regarding file system IO actions.
 */
@Component
@RequiredArgsConstructor
public class FileService {

  private final YAMLMapper yamlMapper;

  private static final Path INPUT_FOLDER = Path.of("input");
  private static final Set<String> YAML_EXTENSIONS = Set.of(".yml", ".yaml");

  /**
   * Read YAML files from the specified input folder and parse them to a list of
   * {@link Resume} objects.
   *
   * @return A list of parsed {@link Resume} objects.
   * @throws IOException When directory is unable to be read.
   */
  public List<Resume> readEntries() throws IOException {
    if (!Files.isDirectory(INPUT_FOLDER)) {
      throw new InputFolderMissingException();
    }

    try (final var stream = Files.list(INPUT_FOLDER)) {
      return stream.filter(Files::isRegularFile)
          .filter(FileService::isYAMLFile)
          .map(path -> yamlMapper.readValue(path.toFile(), Resume.class))
          .collect(toUnmodifiableList());
    }
  }

  private static boolean isYAMLFile(final Path path) {
    final var name = path.getFileName()
        .toString()
        .toLowerCase();

    return YAML_EXTENSIONS.stream()
        .anyMatch(name::endsWith);
  }

}
