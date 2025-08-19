package com.fx.post.adapter.`in`.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.global.resolver.AuthUser
import com.fx.post.adapter.`in`.web.dto.like.LikeSearchParam
import com.fx.post.adapter.`in`.web.dto.like.LikeUsersResponse
import com.fx.post.adapter.`in`.web.dto.post.PostResponse
import com.fx.post.application.`in`.LikeCommandUseCase
import com.fx.post.application.`in`.LikeQueryUseCase
import com.fx.post.application.`in`.dto.LikeQueryCommand
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@WebInputAdapter
@RequestMapping("/api/v1/posts")
class LikeApiAdapter(
    private val likeCommandUseCase: LikeCommandUseCase,
    private val likeQueryUseCase: LikeQueryUseCase
) {

    @PostMapping("/{postId}/likes")
    fun addLike(
        @PathVariable postId: Long,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<Unit?>> =
        Api.OK(
            likeCommandUseCase.addLike(postId, authUser.userId)
        )

    @DeleteMapping("/{postId}/likes")
    fun cancelLike(
        @PathVariable postId: Long,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<Unit?>> =
        Api.OK(
            likeCommandUseCase.cancelLike(postId, authUser.userId)
        )

    @GetMapping("/{postId}/likes")
    fun getLikeUsers(
        @PathVariable postId: Long
    ): ResponseEntity<Api<List<LikeUsersResponse>>> =
        Api.OK(
            LikeUsersResponse.from(likeQueryUseCase.getLikeUsers(postId))
        )


    @GetMapping("/me/likes")
    fun getMyLikedPostsDsl(
        @AuthenticatedUser authUser: AuthUser,
        @ModelAttribute likeSearchParam: LikeSearchParam,
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC, size = 20) pageable: Pageable
    ): ResponseEntity<Api<List<PostResponse>>> =
        Api.OK(
            PostResponse.from(
                likeQueryUseCase.getMyLikedPosts(
                    likeSearchParam.toCommand(authUser.userId, pageable)
                )
            )
        )

}