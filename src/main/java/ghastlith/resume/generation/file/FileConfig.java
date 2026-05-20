package ghastlith.resume.generation.file;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tools.jackson.dataformat.yaml.YAMLMapper;

@Configuration
public class FileConfig {

  @Bean
  YAMLMapper yamlMapper() {
    return new YAMLMapper();
  }

}
