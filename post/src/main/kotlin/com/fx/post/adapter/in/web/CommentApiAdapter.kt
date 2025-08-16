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
    ): ResponseEntity<Api<CommentCreateResponse>> =
        Api.OK(
            CommentCreateResponse(
                commentCommandUseCase.createComment(postId,commentCreateRequest.toCommand(authUser))
                    .id
            )
        )

    @GetMapping("/{postId}/comments")
    fun getComments(@PathVariable postId: Long): ResponseEntity<Api<List<CommentResponse>>> =
        Api.OK(
            CommentResponse.from(
                commentQueryUseCase.getComments(postId)
            )
        )

    @GetMapping("/me/comments")
    fun getMyComments(@AuthenticatedUser authUser: AuthUser): ResponseEntity<Api<List<MyCommentResponse>>> =
        Api.OK(
            MyCommentResponse.from(
                commentQueryUseCase.getMyComments(authUser.userId)
            )
        )

}