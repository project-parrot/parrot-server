package com.fx.post.adapter.out.web.impl

import com.fx.global.annotation.hexagonal.WebOutputAdapter
import com.fx.post.adapter.out.web.client.UserFeignClient
import com.fx.post.adapter.out.web.impl.dto.ProfileCommand
import com.fx.post.application.out.web.UserWebPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

@WebOutputAdapter
class UserWebAdapter(
    private val userFeignClient: UserFeignClient
) : UserWebPort{

    override fun getFollowersInfo(userId: Long): List<Long> {
        return userFeignClient.getFollowersInfo(userId).body ?: emptyList()
    }

    override fun getUsersInfo(userIds: List<Long>): List<ProfileCommand>? =
        userFeignClient.getUsersInfo(userIds).body?.map { ProfileCommand(userId = it.userId, nickname = it.nickname, profileImageUrl = it.profileImageUrl) }


}