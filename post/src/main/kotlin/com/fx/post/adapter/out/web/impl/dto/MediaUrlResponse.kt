package com.fx.post.adapter.out.web.impl.dto

data class MediaUrlResponse(
    val referenceId: Long,
    val mediaUrls: List<String>?
) {
    companion object {
        fun toCommand(mediaUrlResponse: MediaUrlResponse): MediaUrlCommand {
            return MediaUrlCommand(
                referenceId = mediaUrlResponse.referenceId,
                mediaUrls = mediaUrlResponse.mediaUrls
            )
        }
    }
}
