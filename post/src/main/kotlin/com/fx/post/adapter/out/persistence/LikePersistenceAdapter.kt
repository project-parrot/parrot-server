package com.fx.post.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.post.adapter.out.persistence.repository.LikeRepository
import com.fx.post.application.out.LikePersistencePort

@PersistenceAdapter
class LikePersistenceAdapter(
    private val likeRepository: LikeRepository
) : LikePersistencePort {
}