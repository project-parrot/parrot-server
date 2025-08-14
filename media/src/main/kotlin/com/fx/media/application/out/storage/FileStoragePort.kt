package com.fx.media.application.out.storage

import com.fx.media.application.`in`.dto.MediaUploadCommand
import com.fx.media.domain.Media

interface FileStoragePort {
    fun store(mediaUploadCommand: MediaUploadCommand) : Media
}