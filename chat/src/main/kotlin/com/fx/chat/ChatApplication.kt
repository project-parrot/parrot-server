package com.fx.chat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["com.fx.global", "com.fx.chat"])
@EnableDiscoveryClient
@SpringBootApplication
class ChatApplication

fun main(args: Array<String>) {
    runApplication<ChatApplication>(*args)
}