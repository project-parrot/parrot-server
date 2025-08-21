package com.fx.media.application.service

import com.fx.global.dto.UserRole
import com.fx.media.adapter.out.storage.dto.FileStoreCommand
import com.fx.media.application.`in`.MediaCommandUseCase
import com.fx.media.application.`in`.dto.MediaUploadCommand
import com.fx.media.application.out.persistence.MediaPersistencePort
import com.fx.media.application.out.storage.FileStoragePort
import com.fx.media.domain.Media
import com.fx.media.exception.MediaException
import com.fx.media.exception.errorcode.MediaErrorCode
import jakarta.transaction.Transactional
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.springframework.stereotype.Service

@Service
class MediaCommandService(
    private val mediaPersistencePort: MediaPersistencePort,
    private val fileStoragePort: FileStoragePort
) : MediaCommandUseCase {


    @Transactional
    override suspend fun uploadFile(mediaUploadCommand: MediaUploadCommand): List<Media> = coroutineScope {
        validateFileCount(mediaUploadCommand.files.size)

        val medias = mediaUploadCommand.files.map { file ->
            async {fileStoragePort.store(FileStoreCommand.storeFile(file, mediaUploadCommand.context, mediaUploadCommand.userId)) }
        }.awaitAll()

        val savedMedia = medias.map { mediaPersistencePort.save(it) }

        savedMedia
    }

    @Transactional
    override fun deleteFile(mediaId: Long, userId: Long, role: UserRole) {
        val media = mediaPersistencePort.findByIdAndIsDeleted(mediaId) ?: throw MediaException(MediaErrorCode.MEDIA_NOT_FOUND)
        if (userId != media.userId && role != UserRole.ADMIN) {
            throw MediaException(MediaErrorCode.MEDIA_FORBIDDEN)
        }
        mediaPersistencePort.delete(Media.deleteMedia(media))
    }

    private fun validateFileCount(count: Int) {
        if (count <= 0)
            throw MediaException(MediaErrorCode.FILE_NOT_FOUND)
        if (count > 5)
            throw MediaException(MediaErrorCode.TOO_MANY_FILE)
    }

}