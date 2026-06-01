package ghastlith.resume.renderer.content;

import static java.util.logging.Level.SEVERE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.util.XRLog;

import ghastlith.resume.renderer.data.Resume;

/**
 * The content string builder to be used when populating a markdown file with
 * resume data.
 */
public class DocumentContent extends Content {

  static {
    XRLog.listRegisteredLoggers().forEach(logger -> XRLog.setLevel(logger, SEVERE));
  }

  private final TemplateEngine engine;

  private static final String TEMPLATE_NAME = "template/resume.html";
  private static final String RESUME_CONTEXT = "resume";
  private static final String EMPTY_HTML_URI = null;

  public DocumentContent(final Path path, final Resume resume, final TemplateEngine engine) {
    super(path, resume);
    this.engine = engine;
  }

  /**
   * Build document content from current {@link DocumentContent} appended String
   * html templated data and populate it on the generated pdf file from provided
   * path.
   *
   * @throws IOException If unable to create output stream for path.
   */
  public void writeToFile() throws IOException {
    try (final var stream = Files.newOutputStream(getPath())) {
      final var html = renderTemplate();
      final var builder = new PdfRendererBuilder();

      useFonts(builder);

      builder.withHtmlContent(html, EMPTY_HTML_URI);
      builder.toStream(stream);
      builder.run();
    }
  }

  private String renderTemplate() {
    final var resume = getResume();
    final var locale = resume.language().getLocale();

    final var context = new Context(locale);
    context.setVariable(RESUME_CONTEXT, resume);

    return engine.process(TEMPLATE_NAME, context);
  }

  private void useFonts(final PdfRendererBuilder builder) {
    final var fonts = DocumentFont.values();

    for (final var font : fonts) {
      final var supplier = font.getSupplier();
      final var family = font.getFamily();

      builder.useFont(supplier, family);
    }
  }

}
