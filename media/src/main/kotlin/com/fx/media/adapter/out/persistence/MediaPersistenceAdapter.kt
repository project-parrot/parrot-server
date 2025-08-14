package com.fx.media.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.media.adapter.out.persistence.entity.MediaEntity
import com.fx.media.adapter.out.persistence.repository.MediaRepository
import com.fx.media.application.out.persistence.MediaPersistencePort
import com.fx.media.domain.Media

@PersistenceAdapter
class MediaPersistenceAdapter(
    private val mediaRepository: MediaRepository,
) : MediaPersistencePort {

    override fun save(media: Media): Media =
        mediaRepository.save(MediaEntity.from(media)).toDomain()

    override fun findByIdAndIsDeleted(mediaId: Long): Media? =
        mediaRepository.findByIdAndIsDeleted(mediaId, false).orElse(null)?.toDomain()

}