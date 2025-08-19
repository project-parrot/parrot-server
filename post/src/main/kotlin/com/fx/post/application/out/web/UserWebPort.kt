package com.fx.post.application.out.web

import com.fx.post.adapter.out.web.impl.dto.ProfileCommand

interface UserWebPort {

    fun getFollowersInfo(userId: Long): List<Long>?

    fun getUsersInfo(userIds: List<Long>): List<ProfileCommand>?

}