package com.fx.chat.application.port.out.web

interface UserWebPort {

    suspend fun existsUsers(targetUserIds: List<Long>): Boolean

}