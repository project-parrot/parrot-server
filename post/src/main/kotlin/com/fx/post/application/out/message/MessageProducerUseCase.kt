package com.fx.post.application.out.message

import com.fx.global.dto.MediaMappingEventDto

interface MessageProducerUseCase {

    fun sendMapping(mediaMappingEventDto: MediaMappingEventDto)
}