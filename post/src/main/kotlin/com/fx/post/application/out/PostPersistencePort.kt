package com.fx.post.application.out

import com.fx.post.domain.Post
import java.time.LocalDateTime

interface PostPersistencePort {

    fun save(post: Post): Post

    fun existsByUserIdAndCreatedAtBetween(userId: Long, start: LocalDateTime, end: LocalDateTime): Boolean

    fun existsById(postId: Long): Boolean

    fun findByIdAndIsDeletedNot(postId: Long): Post?
}