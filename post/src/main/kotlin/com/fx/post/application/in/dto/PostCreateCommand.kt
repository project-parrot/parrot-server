package com.fx.post.application.`in`.dto


data class PostCreateCommand(
    val userId: Long,
    val content: String? = null,
    //val mediaIds: List<Long>
) {

}