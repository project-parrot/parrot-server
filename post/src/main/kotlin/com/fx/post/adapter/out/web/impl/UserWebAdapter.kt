package com.fx.post.adapter.out.web.impl

import com.fx.post.adapter.out.web.client.UserFeignClient
import com.fx.post.application.out.web.UserWebPort
import org.springframework.stereotype.Component

@Component // WebOutputAdapter 등 추가 시 변경 예정
class UserWebAdapter(
    private val userFeignClient: UserFeignClient
) : UserWebPort{

}