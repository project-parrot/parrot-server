package com.fx.post.application.`in`

import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import com.fx.post.application.`in`.dto.CommentCreateCommand
import com.fx.post.application.`in`.dto.PostCreateCommand
import com.fx.post.application.`in`.dto.PostUpdateCommand
import com.fx.post.domain.Comment
import com.fx.post.domain.Post
import java.time.LocalDateTime

interface PostCommandUseCase {

    fun createPost(postCreateCommand: PostCreateCommand): Post

    fun updatePost(postId:Long, postUpdateCommand: PostUpdateCommand): Post

    fun addLike(postId:Long, userId: Long)

    fun cancelLike(postId:Long, userId:Long)

    fun getLikeUsers(postId: Long): List<Long>

    fun getMyLikedPosts(userId: Long, before: LocalDateTime): List<PostSummaryDto>
}