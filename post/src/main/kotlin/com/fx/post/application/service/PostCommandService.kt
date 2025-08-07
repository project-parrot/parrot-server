package com.fx.post.application.service

import com.fx.post.application.`in`.PostCommandUseCase
import com.fx.post.application.out.CommentPersistencePort
import com.fx.post.application.out.LikePersistencePort
import com.fx.post.application.out.PostMediaPersistencePort
import com.fx.post.application.out.PostPersistencePort
import org.springframework.stereotype.Service

@Service
class PostCommandService(
    private val postPersistencePort: PostPersistencePort,
    private val postMediaPersistencePort: PostMediaPersistencePort,
    private val commentPersistencePort: CommentPersistencePort,
    private val likePersistencePort: LikePersistencePort
) : PostCommandUseCase {
}