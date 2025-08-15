package com.fx.media.application.out.persistence

import com.fx.media.domain.Media

interface MediaPersistencePort {

    fun save(media: Media) : Media

    fun findByIdAndIsDeleted(mediaId: Long) : Media?

    fun delete(media: Media)

    fun findByIdInAndIsDeleted(mediaIds: List<Long>) : List<Media>

}