package ghastlith.resume.renderer.content;

import java.io.IOException;
import java.nio.file.Path;

import ghastlith.resume.renderer.data.Resume;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Abstract implementation of file specific content generators.
 */
@Getter
@RequiredArgsConstructor
public abstract class Content {

  private final Path path;
  private final Resume resume;

  public abstract void writeToFile() throws IOException;

}
