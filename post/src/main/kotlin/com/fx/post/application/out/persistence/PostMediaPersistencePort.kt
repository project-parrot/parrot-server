package com.fx.post.application.out.persistence

import com.fx.post.domain.PostMedia

interface PostMediaPersistencePort {

    fun save(postMedia: PostMedia) : PostMedia
}