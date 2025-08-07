package com.fx.post.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.post.adapter.out.persistence.repository.PostRepository
import com.fx.post.application.out.PostPersistencePort

@PersistenceAdapter
class PostPersistenceAdapter(
    private val postRepository: PostRepository
) : PostPersistencePort {
}