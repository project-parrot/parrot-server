package com.fx.post.application.out.persistence

import com.fx.post.domain.Comment

interface CommentPersistencePort {

    fun save(comment: Comment): Comment

    fun existsById(parentId: Long): Boolean

    fun findByPostIdOrderByCreatedAtAsc(postId: Long): List<Comment>

    fun findByUserIdOrderByCreatedAtDesc(userId: Long): List<Comment>
}