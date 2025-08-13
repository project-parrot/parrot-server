package com.fx.media.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.media.adapter.out.persistence.repository.MediaRepository
import com.fx.media.application.out.persistence.MediaPersistencePort

@PersistenceAdapter
class MediaPersistenceAdapter(
    private val mediaRepository: MediaRepository,
) : MediaPersistencePort {

}