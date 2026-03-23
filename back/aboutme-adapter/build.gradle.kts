plugins {
    kotlin("plugin.jpa") version "1.9.25"
}

tasks {
    bootJar {
        enabled = false
    }
    jar {
        enabled = true
    }
}

dependencies {
    // project
    implementation(project(":aboutme-common"))
    implementation(project(":aboutme-app"))
    implementation(project(":aboutme-core"))

    // main
    api("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // test
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}
