package com.fx.media.application.`in`

import com.fx.media.application.`in`.dto.MediaUploadCommand
import com.fx.media.domain.Media

interface MediaCommandUseCase {

    fun uploadFile(mediaUploadCommand: MediaUploadCommand): Media

}