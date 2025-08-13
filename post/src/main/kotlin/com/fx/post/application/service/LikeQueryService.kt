package com.fx.post.application.service

import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import com.fx.post.application.`in`.LikeQueryUseCase
import com.fx.post.application.out.persistence.LikePersistencePort
import com.fx.post.application.out.persistence.PostPersistencePort
import org.springframework.stereotype.Service

@Service
class LikeQueryService(
    private val likePersistencePort: LikePersistencePort,
    private val postPersistencePort: PostPersistencePort
) : LikeQueryUseCase{

    override fun getLikeUsers(postId: Long): List<Long> {
        val users = likePersistencePort.findByPostId(postId)
        // 유저 모듈에서 FeignClient로 userId들의 닉네임들 가져온 뒤 매핑하기(미구현)
        // 이후 userId나 닉네임 순으로 정렬(미구현)

        return users
    }

    override fun getMyLikedPosts(userId: Long, postId: Long): List<PostSummaryDto> {
        val posts = postPersistencePort.findLikedPostsByUserIdAndPostIdBeforeAndIsDeleted(userId, postId)
        // 유저 모듈에서 FeignClient로 userId들의 닉네임들 가져온 뒤 매핑하기(미구현)

        return posts
    }

}