package com.fx.post.adapter.`in`.web.dto.post

import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import java.time.LocalDateTime

data class PostResponse(
    val postId: Long,
    val userId: Long,
    //val nickname: String,
    //val profileImageUrl: String? = null,
    val content: String?,
    val mediaUrls: List<String>?= null,
    val createdAt: LocalDateTime,
    val likeCount: Long,
    val commentCount: Long
) {
    companion object {
        fun from(postSummaryDto: PostSummaryDto): PostResponse {
            return PostResponse(
                postId = postSummaryDto.id,
                userId = postSummaryDto.userId,
                content = postSummaryDto.content,
                mediaUrls = postSummaryDto.mediaUrls,
                createdAt = postSummaryDto.createdAt,
                likeCount = postSummaryDto.likeCount,
                commentCount = postSummaryDto.commentCount
            )
        }

        fun from(postSummaryDtoList: List<PostSummaryDto>): List<PostResponse> {
            return postSummaryDtoList.map { from(it) }
        }
    }

}