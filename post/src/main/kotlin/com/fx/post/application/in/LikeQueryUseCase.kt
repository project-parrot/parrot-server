package com.fx.post.application.`in`

import com.fx.post.application.out.persistence.dto.PostInfo
import com.fx.post.application.`in`.dto.LikeQueryCommand
import com.fx.post.application.`in`.dto.LikeUserCommand

interface LikeQueryUseCase {

    fun getLikeUsers(postId: Long): List<LikeUserCommand>

    fun getMyLikedPosts(likeQueryCommand: LikeQueryCommand): List<PostInfo>
}