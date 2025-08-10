package com.fx.post.config.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition
class SwaggerConfig {

    @Bean
    fun customOpenAPI(@Value("\${openapi.service.url}") url: String): OpenAPI {
        return OpenAPI()
            .servers(listOf(Server().url(url)))
            .info(
                Info()
                    .title("POST SERVICE API")
                    .version("v1")
            )
    }
}