package com.fx.post.application.`in`

interface LikeCommandUseCase {

    fun addLike(postId:Long, userId: Long)

    fun cancelLike(postId:Long, userId:Long)

}