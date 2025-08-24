package com.fx.media.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.global.dto.Context
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

    override fun delete(media: Media) {
        mediaRepository.save(MediaEntity.from(media)).toDomain()
    }

    override fun findByIdInAndIsDeleted(mediaIds: List<Long>): List<Media> =
        mediaRepository.findByIdInAndIsDeleted(mediaIds, false).map { it.toDomain() }

    override fun findByContextAndReferenceIdAndIsDeleted(context: Context, referenceId: Long): List<Media> =
        mediaRepository.findByContextAndReferenceIdAndIsDeleted(context, referenceId, false).map { it.toDomain() }

    override fun findByContextAndReferenceIdInAndIsDeleted(context: Context, referenceIds: List<Long>): List<Media> =
        mediaRepository.findByContextAndReferenceIdInAndIsDeleted(context, referenceIds, false).map { it.toDomain() }

}