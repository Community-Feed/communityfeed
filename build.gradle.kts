plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.seol"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // DB
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("com.h2database:h2")

    // Lombok
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    //QueryDSL

    implementation ("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor ("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor ("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor ("jakarta.persistence:jakarta.persistence-api")

    // Test
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

val querydslDir = "${layout.projectDirectory}/build/generated/querydsl"

sourceSets {
    getByName("main").java.srcDirs(querydslDir)
}

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory = file(querydslDir)
}

tasks.named("clean") {
    doLast {
        file(querydslDir).deleteRecursively()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
