package ghastlith.resume.file;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tools.jackson.dataformat.yaml.YAMLMapper;

@Configuration
public class FileConfig {

  @Bean
  YAMLMapper yamlMapper() {
    return new YAMLMapper();
  }

  @Bean
  Path inputFolder(@Value("${input-folder}") final String folder) {
    return Path.of(folder);
  }

  @Bean
  Path outputFolder(@Value("${output-folder}") final String folder) {
    return Path.of(folder);
  }

}
