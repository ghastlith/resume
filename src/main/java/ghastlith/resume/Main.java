package ghastlith.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ghastlith.resume.file.FileService;
import ghastlith.resume.renderer.ResumeGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class Main implements CommandLineRunner {

  @Autowired private ApplicationContext context;
  @Autowired private FileService fileService;
  @Autowired private ResumeGenerator resumeGenerator;

  private static final int BASE_ERROR_CODE = 1;

  public static void main(final String[] args) {
    SpringApplication.run(Main.class, args);
  }

  @Override
  public void run(final String... args) throws Exception {
    try {
      final var entries = fileService.readEntries();
      resumeGenerator.generate(entries);
    } catch (Exception e) {
      log.error("error when generating resume(s)", e);
      SpringApplication.exit(context, () -> BASE_ERROR_CODE);
    }
  }

}
