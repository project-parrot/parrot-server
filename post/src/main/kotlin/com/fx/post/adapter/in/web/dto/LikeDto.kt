package com.fx.post.adapter.`in`.web.dto

/*
* 인가 처리 적용 전까지 사용하는 임시 DTO 객체이며, 인가 등록 시 삭제 예정
* 임시로 만든 클래스이므로 Command 객체는 따로 생성하지 않겠음
* */
data class LikeDto(
    val userId: Long
) {
}