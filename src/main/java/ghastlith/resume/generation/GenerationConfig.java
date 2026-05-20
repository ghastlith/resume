package ghastlith.resume.generation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tools.jackson.dataformat.yaml.YAMLMapper;

@Configuration
public class GenerationConfig {

  @Bean
  YAMLMapper yamlMapper() {
    return new YAMLMapper();
  }

}
