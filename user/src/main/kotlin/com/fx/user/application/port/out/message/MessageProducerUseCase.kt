package com.fx.user.application.port.out.message

import com.fx.global.dto.MediaMappingEventDto

interface MessageProducerUseCase {

    fun sendMapping(mediaMappingEventDto: MediaMappingEventDto)

}