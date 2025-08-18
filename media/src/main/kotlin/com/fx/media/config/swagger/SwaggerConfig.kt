package com.fx.media.config.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition
class SwaggerConfig {

    @Bean
    fun customOpenAPI(@Value("\${openapi.service.url}") url: String): OpenAPI {
        val securityScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)
            .name("Authorization")
        val securityRequirement = SecurityRequirement().addList("Bearer")

        return OpenAPI()
            .servers(listOf(Server().url(url)))
            .security(listOf(securityRequirement))
            .components(Components().addSecuritySchemes("Bearer", securityScheme))
            .info(
                Info()
                    .title("MEDIA SERVICE API")
                    .version("v1")
            )
    }
}