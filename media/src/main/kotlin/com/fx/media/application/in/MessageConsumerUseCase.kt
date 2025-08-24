package com.fx.media.application.`in`

import com.fx.global.dto.MediaMappingEventDto


interface MessageConsumerUseCase {

    fun consumeMessage(mediaMappingEventDto: MediaMappingEventDto)

}