package com.fx.post.adapter.`in`.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.global.resolver.AuthUser
import com.fx.post.adapter.`in`.web.dto.comment.*
import com.fx.post.application.`in`.CommentCommandUseCase
import com.fx.post.application.`in`.CommentQueryUseCase
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@WebInputAdapter
@RequestMapping("/api/v1/posts")
class CommentApiAdapter(
    private val commentCommandUseCase: CommentCommandUseCase,
    private val commentQueryUseCase: CommentQueryUseCase
) {

    @Operation(summary = "댓글 작성")
    @PostMapping("/{postId}/comments")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody @Valid commentCreateRequest: CommentCreateRequest,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<CommentCreateResponse>> =
        Api.OK(
            CommentCreateResponse(
                commentCommandUseCase.createComment(
                    commentCreateRequest.toCommand(postId, authUser)
                ).id
            )
        )

    @Operation(summary = "댓글 조회",
        description = "default : [sort=id, ASC], [size=20] <br>" +
            " 요청 예시 : /api/v1/posts/{postId}/comments?sort=id, ASC" +
                "commentId 파라미터 추가 시 해당 파라미터 이후/이전 댓글" +
                "parentId 파라미터 추가 시 해당 댓글의 대댓글 조회")
    @GetMapping("/{postId}/comments")
    fun getCommentsQueryDsl(
        @PathVariable postId: Long,
        @ModelAttribute commentSearchParam: CommentSearchParam,
        @PageableDefault(sort = ["id"], direction = Sort.Direction.ASC, size = 20) pageable: Pageable
    ) : ResponseEntity<Api<List<CommentResponse>>> =
        Api.OK(
            CommentResponse.from(
                commentQueryUseCase.getComments(
                    commentSearchParam.toCommand(postId = postId, pageable = pageable)
                )
            )
        )

    @Operation(summary = "내가 작성한 댓글 조회",
        description = "default : [sort=id, ASC], [size=20] <br>" +
                " 요청 예시 : /api/v1/posts/me/comments?sort=id, ASC" +
                "commentId 파라미터 추가 시 해당 파라미터 이후/이전 댓글")
    @GetMapping("/me/comments")
    fun getMyComments(
        @AuthenticatedUser authUser: AuthUser,
        @ModelAttribute commentSearchParam: CommentSearchParam,
        @PageableDefault(sort = ["id"], direction = Sort.Direction.ASC, size = 20) pageable: Pageable
    ): ResponseEntity<Api<List<MyCommentResponse>>> =
        Api.OK(
            MyCommentResponse.from(
                commentQueryUseCase.getMyComments(
                    commentSearchParam.toCommand(userId = authUser.userId, pageable = pageable)
                )
            )
        )

}