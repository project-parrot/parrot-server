package com.fx.user.config.security

import com.fx.user.adapter.security.CustomOAuth2UserService
import com.fx.user.adapter.security.CustomSuccessHandler
import com.fx.user.adapter.security.JwtAuthenticationFilter
import com.fx.user.application.out.security.JwtProviderPort
import jakarta.servlet.http.HttpServletResponse
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
    private val jwtProviderPort: JwtProviderPort,
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
                        "/open-api/**",
                        "/internal/**"
                    )
                    .permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtProviderPort),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .exceptionHandling { exceptions -> // 기타 예외 응답 처리 -> OAuth Login Html 응답을 방지합니다.
                exceptions
                    .authenticationEntryPoint { request, response, authException ->
                        response.contentType = "application/json"
                        response.status = HttpServletResponse.SC_UNAUTHORIZED
                        response.writer.write("{\"error\": \"Unauthorized\"}")
                    }
            }

        return http.build()
    }

}