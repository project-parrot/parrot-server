package com.fx.post.adapter.`in`.web.dto.post

import com.fx.post.application.out.persistence.dto.PostInfo
import java.time.LocalDateTime

data class PostResponse(
    val postId: Long,
    val userId: Long,
    val nickname: String?= null,
    val profileImageUrl: String? = null,
    val content: String?,
    val mediaUrls: List<String>?= null,
    val createdAt: LocalDateTime,
    val likeCount: Long,
    val commentCount: Long
) {
    companion object {
        fun from(postInfo: PostInfo): PostResponse {
            return PostResponse(
                postId = postInfo.id,
                userId = postInfo.userId,
                nickname = postInfo.nickname,
                profileImageUrl = postInfo.profileImageUrl,
                content = postInfo.content,
                mediaUrls = postInfo.mediaUrls,
                createdAt = postInfo.createdAt,
                likeCount = postInfo.likeCount,
                commentCount = postInfo.commentCount
            )
        }

        fun from(postInfoList: List<PostInfo>): List<PostResponse> {
            return postInfoList.map { from(it) }
        }
    }

}