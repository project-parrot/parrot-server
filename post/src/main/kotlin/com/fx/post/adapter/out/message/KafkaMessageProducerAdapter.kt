package com.fx.post.adapter.out.message

import com.fx.global.annotation.hexagonal.MessageOutputAdapter
import com.fx.global.dto.MediaMappingEventDto
import com.fx.post.application.out.message.MessageProducerUseCase
import org.springframework.kafka.core.KafkaTemplate

@MessageOutputAdapter
class KafkaMessageProducerAdapter(
    private val kafkaTemplate: KafkaTemplate<String, MediaMappingEventDto>,
    ) : MessageProducerUseCase {

    override fun sendMapping(mediaMappingEventDto: MediaMappingEventDto) {
        kafkaTemplate.send("media-mapping", mediaMappingEventDto)
    }
}