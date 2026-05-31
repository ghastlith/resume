package ghastlith.resume.renderer.content;

import java.io.InputStream;

import com.openhtmltopdf.extend.FSSupplier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Set of fonts and their respective designed data to be used when rendering
 * Resume Documents.
 */
@Getter
@RequiredArgsConstructor
public enum DocumentFont {

  ARIAL("/static/fonts/arial.ttf", "Arial"),
  TIMES("/static/fonts/times.ttf", "Times New Roman");

  private final String path;
  private final String family;

  public FSSupplier<InputStream> getSupplier() {
    return () -> DocumentFont.class.getResourceAsStream(path);
  }

}
