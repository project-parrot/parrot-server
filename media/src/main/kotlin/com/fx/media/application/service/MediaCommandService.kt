package com.fx.media.application.service

import com.fx.media.application.`in`.MediaCommandUseCase
import com.fx.media.application.`in`.dto.MediaUploadCommand
import com.fx.media.application.out.persistence.MediaPersistencePort
import com.fx.media.application.out.storage.FileStoragePort
import com.fx.media.domain.Media
import org.springframework.stereotype.Service

@Service
class MediaCommandService(
    private val mediaPersistencePort: MediaPersistencePort,
    private val fileStoragePort: FileStoragePort
) : MediaCommandUseCase {

    override fun uploadFile(mediaUploadCommand: MediaUploadCommand): Media {

        val media = fileStoragePort.store(mediaUploadCommand)
        val savedMedia = mediaPersistencePort.save(media)

        return savedMedia
    }

}