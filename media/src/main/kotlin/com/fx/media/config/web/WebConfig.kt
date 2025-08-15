package com.fx.media.config.web

import com.fx.global.interceptor.AuthorizationInterceptor
import com.fx.global.resolver.AuthenticatedUserResolver
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val authorizationInterceptor: AuthorizationInterceptor,
    @Value("\${file.upload-dir}") private val uploadDir: String,
    @Value("\${file.context-path}") val contextPath: String,
) : WebMvcConfigurer {
    private val OPENAPI: String = "/open-api"

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver?>) {
        resolvers.add(AuthenticatedUserResolver())
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authorizationInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/open-api/**", "/internal/**")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler(
            OPENAPI + contextPath + "**",
        )
            .addResourceLocations("file:$uploadDir")
    }
}