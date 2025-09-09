package com.fx.chat.config.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import org.springframework.web.server.WebFilter

@Configuration
class WebConfig(
    private val reactiveAuthenticatedUserResolver: ReactiveAuthenticatedUserResolver
) : WebFluxConfigurer {

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(reactiveAuthenticatedUserResolver)
    }

    @Bean
    fun reactiveAuthorizationFilter(
        reactiveAuthorizationInterceptor: ReactiveAuthorizationInterceptor
    ): WebFilter {
        return reactiveAuthorizationInterceptor
    }

}