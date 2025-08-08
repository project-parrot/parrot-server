package com.fx.post

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@ComponentScan(basePackages = ["com.fx.post", "com.fx.global"])
@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
class PostApplication

fun main(args: Array<String>) {
    runApplication<PostApplication>(*args)
}
