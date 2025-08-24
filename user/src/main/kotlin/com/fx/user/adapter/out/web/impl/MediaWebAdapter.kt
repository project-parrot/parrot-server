package com.fx.user.adapter.out.web.impl

import com.fx.global.annotation.hexagonal.WebOutputAdapter
import com.fx.global.dto.Context
import com.fx.post.adapter.out.web.impl.dto.MediaUrlResponse
import com.fx.user.adapter.out.web.client.MediaFeignClient
import com.fx.user.adapter.out.web.impl.dto.MediaUrlCommand
import com.fx.user.application.out.web.MediaWebPort

@WebOutputAdapter
class MediaWebAdapter(
    private val mediaFeignClient: MediaFeignClient,
) : MediaWebPort {

    override fun getUrls(context: Context, referenceIds: List<Long>): List<MediaUrlCommand>? =
        mediaFeignClient.getMediaUrls(context, referenceIds).body?.map { MediaUrlResponse.toCommand(it) }

}