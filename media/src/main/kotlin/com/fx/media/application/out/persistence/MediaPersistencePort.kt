package com.fx.media.application.out.persistence

import com.fx.media.domain.Media

interface MediaPersistencePort {

    fun save(media: Media) : Media
}