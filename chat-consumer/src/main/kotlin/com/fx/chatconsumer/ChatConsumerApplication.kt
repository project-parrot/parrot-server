package com.fx.chatconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["com.fx.global", "com.fx.chatconsumer"])
@SpringBootApplication
class ChatConsumerApplication

fun main(args: Array<String>) {
    runApplication<ChatConsumerApplication>(*args)
}
