package com.fx.user.application.out

import com.fx.global.dto.MediaMappingEventDto

interface MessageProducerUseCase {

    fun sendMapping(mediaMappingEventDto: MediaMappingEventDto)

}