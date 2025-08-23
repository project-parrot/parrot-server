package com.fx.post.application.out.persistence

import com.fx.post.domain.Like

interface LikePersistencePort {

    fun save(like: Like): Like

    fun existsByPostIdAndUserId(postId: Long, userId: Long): Boolean

    fun deleteByPostIdAndUserId(like: Like): Int

    fun findByPostId(postId: Long): List<Long>
}