tasks {
    bootJar {
        enabled = false
    }
    jar {
        enabled = true
    }
}

repositories {
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    // ============= project =============
    implementation(project(":aboutme-common"))
    implementation(project(":aboutme-domain"))

    // spring ai
    implementation(platform("org.springframework.ai:spring-ai-bom:1.0.0"))

    // vectordb
    implementation("org.springframework.ai:spring-ai-starter-vector-store-pgvector")
}
