plugins {
  id("java");
  id("org.springframework.boot") version "4.1.0";
  id("io.spring.dependency-management") version "1.1.7";
}

group = "ghastlith";
version = "1.0.0";

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21);
  }
}

repositories {
  mavenCentral();
}

dependencies {
  // spring
  implementation("org.springframework.boot:spring-boot-starter");
  implementation("org.springframework.boot:spring-boot-starter-validation");
  implementation("org.springframework.boot:spring-boot-starter-thymeleaf");

  // tests
  testImplementation("org.springframework.boot:spring-boot-starter-test");
  testImplementation("de.redsix:pdfcompare:1.1.26");
  testRuntimeOnly("org.junit.platform:junit-platform-launcher");

  // lombok
  compileOnly("org.projectlombok:lombok:1.18.38");
  annotationProcessor("org.projectlombok:lombok:1.18.38");
  testCompileOnly("org.projectlombok:lombok:1.18.38");
  testAnnotationProcessor("org.projectlombok:lombok:1.18.38");

  // jackson
  implementation("tools.jackson.core:jackson-databind:3.1.3");
  implementation("tools.jackson.dataformat:jackson-dataformat-yaml:3.1.3");

  // pdf
  implementation("com.openhtmltopdf:openhtmltopdf-pdfbox:1.0.10");
}

tasks.bootJar {
  archiveVersion.set("");
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform();

  testLogging {
    events("passed", "skipped", "failed");
  }
}
