package ghastlith.resume.renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import ghastlith.resume.renderer.data.Resume;
import lombok.RequiredArgsConstructor;

/**
 * The content string builder to be used when populating a markdown file with
 * resume data.
 */
@RequiredArgsConstructor
public class DocumentContent {

  private final Path path;
  private final Resume resume;
  private final TemplateEngine templateEngine;

  private static final String TEMPLATE_NAME = "template/resume.html";
  private static final String RESUME_CONTEXT = "resume";
  private static final String EMPTY_HTML_URI = null;

  /**
   * Build document content from current {@link DocumentContent} appended String
   * html templated data and populate it on the generated pdf file from provided
   * path.
   *
   * @throws IOException If unable to create output stream for path.
   */
  public void writeToFile() throws IOException {
    try (final var stream = Files.newOutputStream(path)) {
      final var builder = new PdfRendererBuilder();

      final var html = renderTemplate();

      builder.withHtmlContent(html, EMPTY_HTML_URI);
      builder.toStream(stream);
      builder.run();
    }
  }

  private String renderTemplate() {
    final var locale = resume.language().getLocale();
    final var context = new Context(locale);

    context.setVariable(RESUME_CONTEXT, resume);

    return templateEngine.process(TEMPLATE_NAME, context);
  }

}
