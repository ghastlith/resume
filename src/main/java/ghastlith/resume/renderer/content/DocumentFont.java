package ghastlith.resume.renderer.content;

import static com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.FontStyle.NORMAL;

import java.io.InputStream;

import com.openhtmltopdf.extend.FSSupplier;
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder.FontStyle;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Set of fonts and their respective designed data to be used when rendering
 * Resume Documents.
 */
@Getter
@RequiredArgsConstructor
public enum DocumentFont {

  GOOGLE("/fonts/google-sans.ttf", "Google Sans", 400, NORMAL, true),
  GOOGLE_BOLD("/fonts/google-sans-bold.ttf", "Google Sans", 600, NORMAL, true),
  TIMES("/fonts/times-new-roman.ttf", "Times New Roman", 400, NORMAL, true),
  TIMES_BOLD("/fonts/times-new-roman-bold.ttf", "Times New Roman", 600, NORMAL, true);

  private final String path;
  private final String family;
  private final Integer weight;
  private final FontStyle style;
  private final boolean isSubset;

  public void registerOn(final PdfRendererBuilder builder) {
    final var supplier = getSupplier();
    builder.useFont(supplier, family, weight, style, isSubset);
  }

  private FSSupplier<InputStream> getSupplier() {
    return () -> DocumentFont.class.getResourceAsStream(path);
  }

}
