package com.fx.media

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@ComponentScan(basePackages = ["com.fx.media", "com.fx.global"])
@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
class MediaApplication

fun main(args: Array<String>) {
    runApplication<MediaApplication>(*args)
}
