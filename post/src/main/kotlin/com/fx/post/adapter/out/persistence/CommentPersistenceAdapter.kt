package com.fx.post.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.post.adapter.out.persistence.repository.CommentRepository
import com.fx.post.application.out.CommentPersistencePort

@PersistenceAdapter
class CommentPersistenceAdapter(
    private val commentRepository: CommentRepository
) : CommentPersistencePort {
}