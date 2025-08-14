package com.fx.media.application.service

import com.fx.media.application.`in`.MediaQueryUseCase
import com.fx.media.application.out.persistence.MediaPersistencePort
import com.fx.media.application.out.storage.FileStoragePort
import com.fx.media.domain.Media
import com.fx.media.exception.MediaException
import com.fx.media.exception.errorcode.MediaErrorCode
import org.springframework.stereotype.Service

@Service
class MediaQueryService(
    private val mediaPersistencePort: MediaPersistencePort,
    private val fileStoragePort: FileStoragePort
) : MediaQueryUseCase {

    override fun getFile(mediaId: Long): Media =
        mediaPersistencePort.findByIdAndIsDeleted(mediaId) ?: throw MediaException(MediaErrorCode.MEDIA_NOT_FOUND)

}