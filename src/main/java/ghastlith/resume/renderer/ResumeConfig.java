package ghastlith.resume.renderer;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.thymeleaf.templatemode.TemplateMode.HTML;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ResumeConfig {

  @Bean
  ClassLoaderTemplateResolver templateResolver() {
    final var resolver = new ClassLoaderTemplateResolver();
    resolver.setTemplateMode(HTML);
    resolver.setCharacterEncoding(UTF_8.name());

    return resolver;
  }

}
