plugins {
  id("java");
  id("org.springframework.boot") version "4.0.6";
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

  // tests
  testImplementation("org.springframework.boot:spring-boot-starter-test");
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
  implementation("com.itextpdf:itextpdf:5.5.13.5");
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
