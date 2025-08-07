package com.fx.post.application.`in`.dto

data class PostCreateCommand(
    //임시로 유저아이디를 받음
    val userId: Long,
    val content: String? = null,
    //val mediaIds: List<Long>
) {

}