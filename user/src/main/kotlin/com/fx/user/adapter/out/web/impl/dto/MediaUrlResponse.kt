package com.fx.post.adapter.out.web.impl.dto

import com.fx.user.adapter.out.web.impl.dto.MediaInfo
import com.fx.user.adapter.out.web.impl.dto.MediaUrlCommand

data class MediaUrlResponse(
    val referenceId: Long,
    val mediaInfos: List<MediaInfo>?
) {
    companion object {
        fun toCommand(mediaUrlResponse: MediaUrlResponse): MediaUrlCommand {
            return MediaUrlCommand(
                referenceId = mediaUrlResponse.referenceId,
                mediaInfos = mediaUrlResponse.mediaInfos
            )
        }
    }
}
