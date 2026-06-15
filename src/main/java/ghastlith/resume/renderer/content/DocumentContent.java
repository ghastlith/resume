package ghastlith.resume.renderer.content;

import static java.util.Arrays.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.util.XRLog;

import ghastlith.resume.renderer.data.Resume;

/**
 * The Document file content builder used when generating pdf files from parsed
 * resume data.
 */
public class DocumentContent extends Content {

  static {
    XRLog.setLoggingEnabled(false);
  }

  private final TemplateEngine engine;

  private static final String TEMPLATE_NAME = "templates/resume.html";
  private static final String RESUME_CONTEXT = "resume";
  private static final String EMPTY_HTML_URI = null;

  public DocumentContent(final Path path, final Resume resume, final TemplateEngine engine) {
    super(path, resume);
    this.engine = engine;
  }

  /**
   * Build document content from thymeleaf templated HTML String based on current
   * {@link DocumentContent} resume data and populate it on the generated pdf file
   * from provided path.
   *
   * @throws IOException If unable to create output stream for path.
   */
  public void writeToFile() throws IOException {
    try (final var stream = Files.newOutputStream(getPath())) {
      final var html = renderTemplate();
      final var builder = new PdfRendererBuilder();

      stream(DocumentFont.values()).forEach(font -> font.registerOn(builder));

      builder.withHtmlContent(html, EMPTY_HTML_URI)
          .toStream(stream)
          .run();
    }
  }

  private String renderTemplate() {
    final var resume = getResume();
    final var locale = resume.language().getLocale();

    final var context = new Context(locale);
    context.setVariable(RESUME_CONTEXT, resume);

    return engine.process(TEMPLATE_NAME, context);
  }

}
