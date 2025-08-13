package com.fx.media.application.service

import com.fx.media.application.`in`.MediaQueryUseCase
import com.fx.media.application.out.persistence.MediaPersistencePort
import com.fx.media.application.out.storage.FileStoragePort
import org.springframework.stereotype.Service

@Service
class MediaQueryService(
    private val mediaPersistencePort: MediaPersistencePort,
    private val fileStoragePort: FileStoragePort
) : MediaQueryUseCase {
}