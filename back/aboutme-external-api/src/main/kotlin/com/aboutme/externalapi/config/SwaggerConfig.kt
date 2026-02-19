package com.aboutme.externalapi.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
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
        val securityRequirement = SecurityRequirement().addList(Elements.BASIC_AUTH)

        return OpenAPI()
            .info(apiInfo())
            .addSecurityItem(securityRequirement)
            .components(securitySchemes())
    }

    @Bean
    fun forwardedHeaderFilter(): ForwardedHeaderFilter {
        return ForwardedHeaderFilter()
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

    private fun securitySchemes(): Components {
        val basicAuthScheme =
            SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("basic")

        return Components().addSecuritySchemes(Elements.BASIC_AUTH, basicAuthScheme)
    }
}
