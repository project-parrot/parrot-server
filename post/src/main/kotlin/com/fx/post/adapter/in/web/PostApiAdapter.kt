package com.fx.post.adapter.`in`.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.global.resolver.AuthUser
import com.fx.post.adapter.`in`.web.dto.*
import com.fx.post.application.`in`.PostCommandUseCase
import com.fx.post.application.`in`.PostQueryUseCase
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@WebInputAdapter
@RequestMapping("/api/v1/posts")
class PostApiAdapter(
    private val postCommandUseCase: PostCommandUseCase,
    private val postQueryUseCase: PostQueryUseCase
) {
    @PostMapping("/")
    fun createPost(
        @RequestBody @Valid postCreateRequest: PostCreateRequest,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<PostCreateResponse>> {
        val post = postCommandUseCase.createPost(postCreateRequest.toCommand(authUser))
        return Api.OK(PostCreateResponse(post.id))
    }

    @GetMapping("/")
    fun getFollowersPosts(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) before: LocalDateTime,
                          @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<List<PostResponse>>> {
        val posts = postQueryUseCase.getFollowersPosts(authUser.userId, before)
        return Api.OK(posts.map { PostResponse.from(it) })
    }

    @GetMapping("/me")
    fun getMyPosts(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) before: LocalDateTime,
                 @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<List<PostResponse>>> {
        val posts = postQueryUseCase.getMyPosts(authUser.userId, before)
        return Api.OK(posts.map { PostResponse.from(it) })
    }

    @GetMapping("/{userId}")
    fun getUserPosts(@PathVariable userId: Long,
                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) before: LocalDateTime
    ): ResponseEntity<Api<List<PostResponse>>> {
        val posts = postQueryUseCase.getUserPosts(userId, before)
        return Api.OK(posts.map { PostResponse.from(it) })
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody @Valid postUpdateRequest: PostUpdateRequest,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<PostUpdateResponse>> {
        val post = postCommandUseCase.updatePost(postId, postUpdateRequest.toCommand(authUser))
        return Api.OK(PostUpdateResponse(post.id))
    }


    @PostMapping("/{postId}/comments")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody @Valid commentCreateRequest: CommentCreateRequest,
        @AuthenticatedUser authUser: AuthUser): ResponseEntity<Api<CommentCreateResponse>> {
        val comment = postCommandUseCase.createComment(postId,commentCreateRequest.toCommand(authUser))
        return Api.OK(CommentCreateResponse(comment.id))
    }

    @GetMapping("/{postId}/comments")
    fun getComments(@PathVariable postId: Long): ResponseEntity<Api<List<CommentResponse>>> {
        val comments = postCommandUseCase.getComments(postId)
        return Api.OK(comments.map { CommentResponse.from(it) })
    }

    @GetMapping("/me/comments")
    fun getMyComments(@AuthenticatedUser authUser: AuthUser): ResponseEntity<Api<List<MyCommentResponse>>> {
        val comments = postCommandUseCase.getMyComments(authUser.userId)
        return Api.OK(comments.map { MyCommentResponse.from(it) })
    }

    @PostMapping("/{postId}/likes")
    fun addLike(
        @PathVariable postId: Long,
        @AuthenticatedUser authUser: AuthUser
        ): ResponseEntity<Api<Unit?>> {
        postCommandUseCase.addLike(postId, authUser.userId)
        return Api.OK(null)
    }

    @DeleteMapping("/{postId}/likes")
    fun cancelLike(
        @PathVariable postId: Long,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<Unit?>> {
        postCommandUseCase.cancelLike(postId, authUser.userId)
        return Api.OK(null)
    }

    @GetMapping("/{postId}/likes")
    fun getLikeUsers(
        @PathVariable postId: Long
    ): ResponseEntity<Api<List<LikeUsersResponse>>> {
        val likeUsers = postCommandUseCase.getLikeUsers(postId)
        return Api.OK(likeUsers.map { LikeUsersResponse(userId = it) })
    }

    @GetMapping("/me/likes")
    fun getMyLikedPosts(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) before: LocalDateTime,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<List<PostResponse>>> {
        val posts = postCommandUseCase.getMyLikedPosts(authUser.userId, before)
        return Api.OK(posts.map { PostResponse.from(it) })
    }

}