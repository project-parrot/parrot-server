package com.fx.post.adapter.out.web.impl.dto

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
