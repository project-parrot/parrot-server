package com.fx.media.adapter.`in`.web.dto

import com.fx.global.dto.FileType
import com.fx.media.domain.Media
import java.time.LocalDateTime

data class MediaGetResponse(
    val mediaId: Long?,
    val url: String,
    val mediaType: FileType,
    val size: Long,
    val createdAt: LocalDateTime?,
    val isDeleted: Boolean
) {
    companion object {
        @JvmStatic
        fun from(media: Media): MediaGetResponse {
            return MediaGetResponse(
                mediaId = media.id,
                url = media.fileUrl,
                mediaType = media.fileType,
                size = media.fileSize,
                createdAt = media.createdAt,
                isDeleted = media.isDeleted

            )
        }
    }
}