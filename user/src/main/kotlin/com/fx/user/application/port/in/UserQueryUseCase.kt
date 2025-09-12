package com.fx.user.application.port.`in`

interface UserQueryUseCase {

    /**
     * 단 하나라도 User 가 존재하지 않으면 False
     */
    fun existsUsers(userIds: List<Long>): Boolean

}