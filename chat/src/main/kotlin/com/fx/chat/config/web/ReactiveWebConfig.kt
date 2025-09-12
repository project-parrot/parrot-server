package com.fx.chat.config.web

import com.fx.global.filter.ReactiveAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import org.springframework.web.server.WebFilter

@Configuration
class ReactiveWebConfig(
    private val reactiveAuthenticatedUserResolver: ReactiveAuthenticatedUserResolver,
    private val reactiveAuthorizationFilter: ReactiveAuthorizationFilter
) : WebFluxConfigurer {

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(reactiveAuthenticatedUserResolver)
    }

    @Bean
    fun authorizationWebFilter(): WebFilter =
        reactiveAuthorizationFilter

}