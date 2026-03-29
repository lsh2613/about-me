package com.aboutme.adapter.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.Elements
import org.springframework.web.filter.ForwardedHeaderFilter

@Configuration
class SwaggerConfig(
    private val environment: Environment,
) {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(apiInfo())
    }

    @Bean
    fun forwardedHeaderFilter(): ForwardedHeaderFilter {
        return ForwardedHeaderFilter()
    }

    @Bean
    fun adminApiGroup(): GroupedOpenApi {
        return GroupedOpenApi
            .builder()
            .group("admin")
            .pathsToMatch("/admin/**")
            .addOpenApiCustomizer { openApi ->
                val securityRequirement = SecurityRequirement().addList(Elements.BASIC_AUTH)
                openApi.addSecurityItem(securityRequirement)

                val components = openApi.components ?: Components()
                components.addSecuritySchemes(Elements.BASIC_AUTH, basicAuthScheme())
                openApi.components(components)
            }
            .build()
    }

    @Bean
    fun publicApiGroup(): GroupedOpenApi {
        return GroupedOpenApi
            .builder()
            .group("public")
            .pathsToExclude("/admin/**")
            .build()
    }

    private fun extractActiveProfile(): String {
        val profiles: Array<String> = environment.activeProfiles
        return if (profiles.isNotEmpty()) profiles.joinToString(",") else "default"
    }

    private fun apiInfo(): Info {
        val profiles = extractActiveProfile()
        return Info()
            .title("Aboutme API ")
            .description("profiles = ($profiles)")
            .version("v1.0.0")
    }

    private fun basicAuthScheme(): SecurityScheme {
        return SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("basic")
    }
}
