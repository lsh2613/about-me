tasks {
    bootJar {
        enabled = false
    }
    jar {
        enabled = true
    }
}

dependencies {
    // logging
    implementation("org.slf4j:slf4j-api")

    // swagger
    api("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.0")
}
