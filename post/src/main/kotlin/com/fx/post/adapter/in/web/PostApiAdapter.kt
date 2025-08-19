package com.fx.post.adapter.`in`.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.global.resolver.AuthUser
import com.fx.post.adapter.`in`.web.dto.post.*
import com.fx.post.application.`in`.PostCommandUseCase
import com.fx.post.application.`in`.PostQueryUseCase
import io.swagger.v3.oas.annotations.Operation
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

    @Operation(summary = "게시글 작성")
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

    @Operation(summary = "내가 팔로워한 사용자 게시글 조회",
        description = "default : [sort=id,DESC], [size:20] <br>" +
        " 요청 예시 : /api/v1/posts?sort=id,DESC&size=20"
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
                    postSearchParam.toCommand(userId = authUser.userId, pageable = pageable)
                )
            )
        )

    @Operation(summary = "내 게시글 조회",
        description = "default : [sort=id,DESC], [size:20] <br>" +
                " 요청 예시 : /api/v1/posts/me?sort=id,DESC&size=20"
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

    @Operation(summary = "특정 사용자 게시글 조회",
        description = "default : [sort=id,DESC], [size:20] <br>" +
                " 요청 예시 : /api/v1/posts/{userId}?sort=id,DESC&size=20"
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

    @Operation(summary = "게시글 수정",
        description = "Body의 mediaIds에 따라 해당 게시글의 PostMedia가 수정되므로 주의 <br>" +
            "기존 mediaIds=[1, 2]가 일 때 body의 mediaIds=[1, 3]로 보내면 2는 지워지고 3은 추가됨"
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