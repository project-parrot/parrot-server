package com.fx.post.adapter.`in`.web.dto.like

data class LikeUsersResponse(
    val userId: Long,
    //val nickname: String,
    //val profileImageUrl: String? = null
) {
   companion object {
       fun from(userId: Long): LikeUsersResponse {
           return LikeUsersResponse(
               userId = userId
           )
       }

       fun from(userIdList: List<Long>) : List<LikeUsersResponse> {
           return userIdList.map { LikeUsersResponse.from(it) }
       }
   }
}