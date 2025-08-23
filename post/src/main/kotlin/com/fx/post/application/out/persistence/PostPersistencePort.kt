package com.fx.post.application.out.persistence

import com.fx.post.application.out.persistence.dto.PostInfo
import com.fx.post.domain.LikeQuery
import com.fx.post.domain.Post
import com.fx.post.domain.PostQuery
import java.time.LocalDateTime

interface PostPersistencePort {

    fun save(post: Post): Post

    fun existsByUserIdAndCreatedAtBetween(userId: Long, start: LocalDateTime, end: LocalDateTime): Boolean

    fun existsById(postId: Long): Boolean

    fun findByIdAndIsDeletedNot(postId: Long): Post?

    fun getPosts(postQuery: PostQuery): List<PostInfo>

    fun getLikedPosts(likeQuery: LikeQuery): List<PostInfo>

}