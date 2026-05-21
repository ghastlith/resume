package ghastlith.resume.renderer;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import ghastlith.resume.renderer.data.Resume;
import jakarta.validation.Valid;

/**
 * Manages and performs all resume generation related operations.
 */
@Component
@Validated
public class ResumeRenderer {

  /**
   * Generate supported resume files for every YAML data file present on designed
   * input folder.
   *
   * @param entries the resume entries parsed from YAML inputs
   * @return
   */
  public List<File> generate(@Valid final List<Resume> entries) {
    throw new RuntimeException("method not implemented yet");
  }

}
