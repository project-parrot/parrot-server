package com.fx.chat.config.web

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    @Value("\${user-service.url}") private val userServiceUrl: String
) {

    @Bean
    fun userWebClient(): WebClient =
        WebClient.builder()
            .baseUrl(userServiceUrl)
            .build()

}