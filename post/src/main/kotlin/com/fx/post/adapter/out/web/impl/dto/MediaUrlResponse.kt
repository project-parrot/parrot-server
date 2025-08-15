package com.fx.post.adapter.out.web.impl.dto

data class MediaUrlResponse(
    val mediaId: Long,
    val mediaUrl: String
) {
    companion object {
        fun toCommand(mediaUrlResponse: MediaUrlResponse): MediaUrlCommand {
            return MediaUrlCommand(
                mediaId = mediaUrlResponse.mediaId,
                mediaUrl = mediaUrlResponse.mediaUrl
            )
        }
    }
}
