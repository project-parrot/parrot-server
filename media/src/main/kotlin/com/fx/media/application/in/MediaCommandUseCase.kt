package com.fx.media.application.`in`

import com.fx.global.dto.UserRole
import com.fx.media.application.`in`.dto.MediaUploadCommand
import com.fx.media.domain.Media

interface MediaCommandUseCase {

    suspend fun uploadFile(mediaUploadCommand: MediaUploadCommand): List<Media>

    fun deleteFile(mediaId: Long, userId: Long, role: UserRole)

}