package com.fx.post.adapter.`in`.web.dto.like

import com.fx.post.application.`in`.dto.LikeUserCommand

data class LikeUsersResponse(
    val userId: Long,
    val nickname: String?,
    val profileImageUrl: String? = null
) {
   companion object {
       fun from(likeUserCommand: LikeUserCommand): LikeUsersResponse {
           return LikeUsersResponse(
               userId = likeUserCommand.userId,
               nickname = likeUserCommand.nickname,
               profileImageUrl = likeUserCommand.profileImageUrl
           )
       }

       fun from(likeUserCommandList: List<LikeUserCommand>) : List<LikeUsersResponse> {
           return likeUserCommandList.map { LikeUsersResponse.from(it) }
       }
   }
}