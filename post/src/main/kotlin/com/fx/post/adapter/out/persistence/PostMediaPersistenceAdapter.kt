package com.fx.post.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.post.adapter.out.persistence.repository.PostMediaRepository
import com.fx.post.application.out.persistence.PostMediaPersistencePort

@PersistenceAdapter
class PostMediaPersistenceAdapter(
    private val postMediaRepository: PostMediaRepository
) : PostMediaPersistencePort {
}