package com.fx.post.application.`in`

import com.fx.post.application.`in`.dto.CommentCreateCommand
import com.fx.post.domain.Comment
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@Validated
interface CommentCommandUseCase {

    fun createComment(@Valid commentCreateCommand: CommentCreateCommand): Comment

}