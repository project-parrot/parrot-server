package com.fx.post.application.out

import com.fx.post.domain.Like

interface LikePersistencePort {

    fun save(like: Like): Like

    fun existsByPostIdAndUserId(postId: Long, userId: Long): Boolean

    fun deleteByPostIdAndUserId(postId: Long, userId: Long): Int
}