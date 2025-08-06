package com.fx.user.config.security

import com.fx.user.adapter.security.CustomOAuth2UserService
import com.fx.user.adapter.security.CustomSuccessHandler
import com.fx.user.adapter.security.JwtAuthenticationFilter
import com.fx.user.application.out.JwtProviderPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val securityPort: JwtProviderPort,
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val customSuccessHandler: CustomSuccessHandler
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .oauth2Login { oauth2 ->
                oauth2.userInfoEndpoint { userInfoEndpointConfig ->
                    userInfoEndpointConfig.userService(
                        customOAuth2UserService
                    )
                }
                    .successHandler(customSuccessHandler)
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/open-api/**"
                    )
                    .permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                JwtAuthenticationFilter(securityPort),
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }

}