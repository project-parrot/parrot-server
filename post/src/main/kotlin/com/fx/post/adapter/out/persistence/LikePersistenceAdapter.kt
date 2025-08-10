package com.fx.post.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.post.adapter.out.persistence.entity.LikeEntity
import com.fx.post.adapter.out.persistence.repository.LikeRepository
import com.fx.post.application.out.persistence.LikePersistencePort
import com.fx.post.domain.Like

@PersistenceAdapter
class LikePersistenceAdapter(
    private val likeRepository: LikeRepository
) : LikePersistencePort {

    override fun save(like: Like): Like =
        likeRepository.save(LikeEntity.from(like)).toDomain()

    override fun existsByPostIdAndUserId(postId: Long, userId: Long): Boolean =
        likeRepository.existsByPostIdAndUserId(postId, userId)

    override fun deleteByPostIdAndUserId(postId: Long, userId: Long): Int =
        likeRepository.deleteByPostIdAndUserId(postId, userId)

    override fun findByPostId(postId: Long): List<Long> =
        likeRepository.findByPostId(postId).map{ it.toDomain().userId }
}