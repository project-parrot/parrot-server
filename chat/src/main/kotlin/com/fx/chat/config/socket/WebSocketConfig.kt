package com.fx.chat.config.socket

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping

@Configuration
class WebSocketConfig {

    private val log = LoggerFactory.getLogger(WebSocketConfig::class.java)

    @Bean
    fun handleMapping(webSocketHandler: WebSocketHandler): HandlerMapping {
        return SimpleUrlHandlerMapping(mapOf("/api/ws/connect" to webSocketHandler), -1)
    }
}