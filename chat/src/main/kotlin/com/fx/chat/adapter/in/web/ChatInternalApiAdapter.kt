package com.fx.chat.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebInputAdapter
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@WebInputAdapter
@RequestMapping("/internal")
class ChatInternalApiAdapter {
    @PostMapping("/chat")
    fun sendMessage() {

    }
}