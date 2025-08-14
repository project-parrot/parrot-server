package com.fx.post.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.post.adapter.out.persistence.entity.PostMediaEntity
import com.fx.post.adapter.out.persistence.repository.PostMediaRepository
import com.fx.post.application.out.persistence.PostMediaPersistencePort
import com.fx.post.domain.PostMedia

@PersistenceAdapter
class PostMediaPersistenceAdapter(
    private val postMediaRepository: PostMediaRepository
) : PostMediaPersistencePort {

    override fun save(postMedia: PostMedia): PostMedia =
        postMediaRepository.save(PostMediaEntity.from(postMedia)).toDomain()

}