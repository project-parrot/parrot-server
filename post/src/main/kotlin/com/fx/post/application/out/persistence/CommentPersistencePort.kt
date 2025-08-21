package com.fx.post.application.out.persistence

import com.fx.post.domain.Comment
import com.fx.post.domain.CommentInfo
import com.fx.post.domain.CommentQuery

interface CommentPersistencePort {

    fun save(comment: Comment): Comment

    fun existsById(parentId: Long): Boolean

    fun getComments(commentQuery: CommentQuery): List<CommentInfo>
}