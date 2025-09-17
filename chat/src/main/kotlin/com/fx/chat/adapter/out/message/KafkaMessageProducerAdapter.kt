package com.fx.chat.adapter.out.message

import com.fx.chat.application.port.out.message.MessageProducerUseCase
import com.fx.global.annotation.hexagonal.MessageOutputAdapter
import com.fx.global.dto.ChatMessageEventDto
import org.springframework.kafka.core.KafkaTemplate

@MessageOutputAdapter
class KafkaMessageProducerAdapter(
    private val kafkaTemplate: KafkaTemplate<String, ChatMessageEventDto>
) : MessageProducerUseCase {

    override fun sendMessage(chatMessageEventDto: ChatMessageEventDto) {
        kafkaTemplate.send("chat-message", chatMessageEventDto)
    }

}