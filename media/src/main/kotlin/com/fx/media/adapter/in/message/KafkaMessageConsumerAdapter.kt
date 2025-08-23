package com.fx.media.adapter.`in`.message

import com.fx.global.annotation.hexagonal.MessageInputAdapter
import com.fx.global.dto.Context
import com.fx.global.dto.MediaMappingEventDto
import com.fx.media.application.`in`.MediaCommandUseCase
import com.fx.media.application.`in`.MessageConsumerUseCase
import org.springframework.kafka.annotation.KafkaListener

@MessageInputAdapter
class KafkaMessageConsumerAdapter(
    private val mediaCommandUseCase: MediaCommandUseCase,
) : MessageConsumerUseCase {

    @KafkaListener(topics = ["media-mapping"], groupId = "\\\${kafka.consumer.group-id}")
    override fun consumeMessage(mediaMappingEventDto: MediaMappingEventDto) {
        if (mediaMappingEventDto.context == Context.PROFILE) {
            val mediaIds = mediaMappingEventDto.mediaIds
            if (mediaIds.isNullOrEmpty() || mediaIds.size > 1) {
                throw IllegalArgumentException("PROFILE context에서는 mediaIds가 정확히 1개여야 합니다. 현재: ${mediaIds?.size ?: 0}")
            }
        }

        mediaCommandUseCase.mediaMapping(mediaMappingEventDto)
    }


}