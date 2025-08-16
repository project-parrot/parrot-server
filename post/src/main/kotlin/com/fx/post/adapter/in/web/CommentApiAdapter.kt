package com.fx.post.adapter.`in`.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.global.resolver.AuthUser
import com.fx.post.adapter.`in`.web.dto.comment.CommentCreateRequest
import com.fx.post.adapter.`in`.web.dto.comment.CommentCreateResponse
import com.fx.post.adapter.`in`.web.dto.comment.CommentResponse
import com.fx.post.adapter.`in`.web.dto.comment.MyCommentResponse
import com.fx.post.application.`in`.CommentCommandUseCase
import com.fx.post.application.`in`.CommentQueryUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@WebInputAdapter
@RequestMapping("/api/v1/posts")
class CommentApiAdapter(
    private val commentCommandUseCase: CommentCommandUseCase,
    private val commentQueryUseCase: CommentQueryUseCase
) {
    @PostMapping("/{postId}/comments")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody @Valid commentCreateRequest: CommentCreateRequest,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<CommentCreateResponse>> {
        val comment = commentCommandUseCase.createComment(postId,commentCreateRequest.toCommand(authUser))
        return Api.OK(CommentCreateResponse(comment.id))
    }

    @GetMapping("/{postId}/comments")
    fun getComments(@PathVariable postId: Long): ResponseEntity<Api<List<CommentResponse>>> {
        val comments = commentQueryUseCase.getComments(postId)
        return Api.OK(comments.map { CommentResponse.from(it) })
    }

    @GetMapping("/me/comments")
    fun getMyComments(@AuthenticatedUser authUser: AuthUser): ResponseEntity<Api<List<MyCommentResponse>>> {
        val comments = commentQueryUseCase.getMyComments(authUser.userId)
        return Api.OK(comments.map { MyCommentResponse.from(it) })
    }
}