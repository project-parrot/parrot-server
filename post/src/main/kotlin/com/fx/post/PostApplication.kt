package com.fx.post

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
class PostApplication

fun main(args: Array<String>) {
    runApplication<PostApplication>(*args)
}
