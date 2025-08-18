package com.fx.post.adapter.`in`.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.global.resolver.AuthUser
import com.fx.post.adapter.`in`.web.dto.post.*
import com.fx.post.application.`in`.PostCommandUseCase
import com.fx.post.application.`in`.PostQueryUseCase
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@WebInputAdapter
@RequestMapping("/api/v1/posts")
class PostApiAdapter(
    private val postCommandUseCase: PostCommandUseCase,
    private val postQueryUseCase: PostQueryUseCase,
) {
    @PostMapping
    fun createPost(
        @RequestBody @Valid postCreateRequest: PostCreateRequest,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<PostCreateResponse>> =
        Api.OK(
            PostCreateResponse(
                postCommandUseCase.createPost(
                    postCreateRequest.toCommand(authUser)
                ).id
            )
        )

    @GetMapping()
    fun getFollowersPosts(
        @AuthenticatedUser authUser: AuthUser,
        @ModelAttribute postSearchParam: PostSearchParam,
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC, size = 20) pageable: Pageable
    ): ResponseEntity<Api<List<PostResponse>>> =
        Api.OK(
            PostResponse.from(
                postQueryUseCase.getFollowersPosts(
                    postSearchParam.toCommand(pageable = pageable)
                )
            )
        )


    @GetMapping("/me")
    fun getMyPosts(
        @AuthenticatedUser authUser: AuthUser,
        @ModelAttribute postSearchParam: PostSearchParam,
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC, size = 20) pageable: Pageable
    ): ResponseEntity<Api<List<PostResponse>>> =
        Api.OK(
            PostResponse.from(
                postQueryUseCase.getUserPosts(
                    postSearchParam.toCommand(authUser.userId, pageable)
                )
            )
        )

    @GetMapping("/{userId}")
    fun getUserPosts(
        @AuthenticatedUser authUser: AuthUser,
        @PathVariable userId: Long,
        @ModelAttribute postSearchParam: PostSearchParam,
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC, size = 20) pageable: Pageable
    ): ResponseEntity<Api<List<PostResponse>>> =
        Api.OK(
            PostResponse.from(
                postQueryUseCase.getUserPosts(
                    postSearchParam.toCommand(userId, pageable)
                )
            )
        )

    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody @Valid postUpdateRequest: PostUpdateRequest,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<PostUpdateResponse>> =
        Api.OK(
            PostUpdateResponse(
                postCommandUseCase.updatePost(
                    postId, postUpdateRequest.toCommand(authUser)
                ).id
            )
        )

}