package com.fx.post.application.out

import com.fx.post.domain.Comment

interface CommentPersistencePort {

    fun save(comment: Comment): Comment

    fun existsById(parentId: Long): Boolean

}