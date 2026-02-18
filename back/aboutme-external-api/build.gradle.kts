tasks {
    bootJar {
        enabled = true
    }
    jar {
        enabled = false
    }
}

dependencies {
    // ============= project =============
    implementation(project(":aboutme-common"))
    implementation(project(":aboutme-domain"))

    // ============= main =============
    // init
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // swagger
//    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.0")

    // ============= test =============
    testImplementation("org.springframework.security:spring-security-test")
}
