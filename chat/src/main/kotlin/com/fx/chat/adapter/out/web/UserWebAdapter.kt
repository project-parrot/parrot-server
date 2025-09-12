package com.fx.chat.adapter.out.web

import com.fx.chat.application.port.out.web.UserWebPort
import com.fx.global.annotation.hexagonal.WebOutputAdapter
import com.fx.global.api.Api
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@WebOutputAdapter
class UserWebAdapter(
    private val userWebClient: WebClient
) : UserWebPort {

    override suspend fun existsUsers(targetUserIds: List<Long>): Boolean {
        val response = userWebClient.post()
            .uri("/internal/users/exists")
            .bodyValue(mapOf("userIds" to targetUserIds))
            .retrieve()
            .bodyToMono<Api<Boolean>>()
            .awaitSingle()

        return response.data ?: false
    }

}