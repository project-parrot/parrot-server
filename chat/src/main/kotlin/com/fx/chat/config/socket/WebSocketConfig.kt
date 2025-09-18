package com.fx.chat.config.socket

import com.fx.chat.adapter.`in`.socket.ChatSocketAdapter
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping

@Configuration
class WebSocketConfig(
    private val chatSocketAdapter: ChatSocketAdapter
) {

    private val log = LoggerFactory.getLogger(WebSocketConfig::class.java)

    @Bean
    fun handleMapping(): HandlerMapping {
        return SimpleUrlHandlerMapping(mapOf("/api/ws/connect" to chatSocketAdapter), -1)
    }
}