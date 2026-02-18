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

    // init
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.data:spring-data-jpa")

    // dbms
    runtimeOnly("org.postgresql:postgresql")
}
