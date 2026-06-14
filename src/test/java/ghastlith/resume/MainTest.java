package ghastlith.resume;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.util.StreamUtils.copyToString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.core.io.Resource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.thymeleaf.TemplateEngine;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import de.redsix.pdfcompare.PdfComparator;
import ghastlith.resume.file.FileService;
import ghastlith.resume.renderer.content.DocumentFont;
import ghastlith.resume.renderer.data.Resume;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class MainTest {

  @Autowired private Main main;
  @Autowired private ObjectMapper yamlMapper;
  @MockitoSpyBean private FileService fileService;
  @MockitoSpyBean private TemplateEngine templateEngine;

  @Value("classpath:john-constantine.md")
  private Resource expectedMarkdownFile;
  @Value("classpath:john-constantine.html")
  private Resource expectedHTMLFile;

  private static final Path INPUT_YAML_PATH = Path.of("input/example.yml");
  private static final Path OUTPUT_MARKDOWN_PATH = Path.of("output/(cv-en) john constantine.md");
  private static final Path OUTPUT_DOCUMENT_PATH = Path.of("output/(cv-en) john constantine.pdf");
  private static final Path OUTPUT_EXPECTED_PATH = Path.of("output/expected.pdf");

  @BeforeAll
  @AfterAll
  public static void cleanUp() throws IOException {
    Files.deleteIfExists(OUTPUT_MARKDOWN_PATH);
    Files.deleteIfExists(OUTPUT_DOCUMENT_PATH);
    Files.deleteIfExists(OUTPUT_EXPECTED_PATH);
  }

  @Test
  void integrationTest(final CapturedOutput output) throws Exception {
    // given
    final var expectedMarkdown = expectedMarkdownFile.getContentAsString(UTF_8);
    final var resume = yamlMapper.readValue(INPUT_YAML_PATH.toFile(), Resume.class);
    final var entries = singletonList(resume);
    doReturn(entries).when(fileService).readEntries();
    setupExpectedDocument();

    // when
    main.run();

    // then
    assertThat(OUTPUT_DOCUMENT_PATH).exists();
    assertThat(OUTPUT_MARKDOWN_PATH).exists();

    assertThat(Files.readString(OUTPUT_MARKDOWN_PATH)).isEqualTo(expectedMarkdown);
    assertThat(new PdfComparator<>(OUTPUT_EXPECTED_PATH, OUTPUT_DOCUMENT_PATH).compare().isEqual()).isTrue();

    assertThat(output).contains("[DOCUMENT] resume file was created at: output/(cv-en) john constantine.pdf");
    assertThat(output).contains("[MARKDOWN] resume file was created at: output/(cv-en) john constantine.md");
  }

  private void setupExpectedDocument() throws IOException {
    final var stream = Files.newOutputStream(OUTPUT_EXPECTED_PATH);
    final var html = copyToString(expectedHTMLFile.getInputStream(), UTF_8);
    final var builder = new PdfRendererBuilder();

    stream(DocumentFont.values()).forEach(font -> font.registerOn(builder));
    builder.withHtmlContent(html, "").toStream(stream).run();
  }

}
