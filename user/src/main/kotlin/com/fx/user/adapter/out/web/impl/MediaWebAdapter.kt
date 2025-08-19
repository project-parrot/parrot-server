package com.fx.user.adapter.out.web.impl

import com.fx.global.annotation.hexagonal.WebOutputAdapter
import com.fx.post.adapter.out.web.impl.dto.MediaUrlCommand
import com.fx.post.adapter.out.web.impl.dto.MediaUrlResponse
import com.fx.user.adapter.out.web.client.MediaFeignClient
import com.fx.user.application.out.MediaWebPort

@WebOutputAdapter
class MediaWebAdapter(
    private val mediaFeignClient: MediaFeignClient,
) : MediaWebPort {

    override fun getUrl(mediaIds: List<Long>): List<MediaUrlCommand>? =
        mediaFeignClient.getUrl(mediaIds).body?.map { MediaUrlResponse.toCommand(it) }

}