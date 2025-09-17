package com.fx.chatconsumer.adapter.`in`.message

import com.fx.chatconsumer.application.port.`in`.message.MessageConsumerUseCase
import com.fx.global.dto.ChatMessageEventDto
import org.springframework.kafka.annotation.KafkaListener

class KafkaMessageConsumerAdapter(

) : MessageConsumerUseCase {

    @KafkaListener(topics = ["chat-message"], groupId = "\${spring.kafka.consumer.group-id}")
    override fun consumeMessage(chatMessageEventDto: ChatMessageEventDto) {
        // TODO 1. 채팅 메시지 DTO에서 방 번호를 가져와서 Redis에서 비교하기
        // Todo 2. WebClient를 통해 단일 혹은 배치로 Post 요청 보내기
    }

}