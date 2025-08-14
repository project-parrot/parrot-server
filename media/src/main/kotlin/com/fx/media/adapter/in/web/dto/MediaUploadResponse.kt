package com.fx.media.adapter.`in`.web.dto

import com.fx.global.dto.FileType
import com.fx.media.domain.Media

data class MediaUploadResponse(
    val mediaId: Long?= null,
    val url: String,
    val mediaType: FileType,
    val size: Long
) {
    companion object {
        @JvmStatic
        fun from(media: Media): MediaUploadResponse {
            return MediaUploadResponse(
                mediaId = media.id,
                url = media.fileUrl,
                mediaType = media.fileType,
                size = media.fileSize
            )
        }
    }
}
