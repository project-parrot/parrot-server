package com.fx.media.application.service

import com.fx.media.adapter.out.storage.dto.FileStoreCommand
import com.fx.media.application.`in`.MediaCommandUseCase
import com.fx.media.application.`in`.dto.MediaUploadCommand
import com.fx.media.application.out.persistence.MediaPersistencePort
import com.fx.media.application.out.storage.FileStoragePort
import com.fx.media.domain.Media
import com.fx.media.exception.MediaException
import com.fx.media.exception.errorcode.MediaErrorCode
import org.springframework.stereotype.Service

@Service
class MediaCommandService(
    private val mediaPersistencePort: MediaPersistencePort,
    private val fileStoragePort: FileStoragePort
) : MediaCommandUseCase {

    override suspend fun uploadFile(mediaUploadCommand: MediaUploadCommand): List<Media> {
        validateFileCount(mediaUploadCommand.files.size)

        val medias = mediaUploadCommand.files.map { fileStoragePort.store(FileStoreCommand.storeFile(it, mediaUploadCommand.context, mediaUploadCommand.userId)) }
        val savedMedia = medias.map { mediaPersistencePort.save(it) }

        return savedMedia
    }

    private fun validateFileCount(count: Int) {
        if (count <= 0)
            throw MediaException(MediaErrorCode.FILE_NOT_FOUND)
        if (count > 5)
            throw MediaException(MediaErrorCode.TOO_MANY_FILE)
    }

}