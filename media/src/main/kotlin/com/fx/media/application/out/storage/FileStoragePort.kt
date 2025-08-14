package com.fx.media.application.out.storage

import com.fx.media.application.`in`.dto.MediaUploadCommand
import com.fx.media.domain.Media

interface FileStoragePort {
    suspend fun store(mediaUploadCommand: MediaUploadCommand) : Media

    fun storeWithoutCoroutine(mediaUploadCommand: MediaUploadCommand) : Media

}