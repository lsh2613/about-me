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
}
