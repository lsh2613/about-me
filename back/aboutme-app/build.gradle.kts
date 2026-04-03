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
    implementation(project(":aboutme-core"))

    // main
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.jsoup:jsoup:1.22.1")

    // test
    testImplementation("io.mockk:mockk:1.14.9")
}
