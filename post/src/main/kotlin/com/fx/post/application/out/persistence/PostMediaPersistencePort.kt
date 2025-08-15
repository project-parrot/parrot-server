package com.fx.post.application.out.persistence

import com.fx.post.domain.PostMedia

interface PostMediaPersistencePort {

    fun save(postMedia: PostMedia) : PostMedia

    fun findByPostId(postId: Long): List<Long>

    fun delete(postId:Long, mediaId:Long)

    fun findByPostIdIn(postIds: List<Long>): List<PostMedia>
}