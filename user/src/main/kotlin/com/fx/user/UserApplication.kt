package com.fx.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
class UserApplication

fun main(args: Array<String>) {
    runApplication<UserApplication>(*args)
}