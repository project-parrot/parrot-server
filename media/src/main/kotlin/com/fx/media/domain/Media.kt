package com.fx.media.domain

import com.fx.global.dto.Context
import com.fx.global.dto.FileType
import java.time.LocalDateTime

data class Media (
    val id: Long?= null,
    val fileUrl: String,
    val fileType: FileType,
    val fileSize: Long,
    val userId: Long?= null,
    val serverName: String,
    val originalName: String,
    val extension: String,
    val context: Context,
    val referenceId: Long?= null,
    val isDeleted: Boolean = false,
    val createdAt: LocalDateTime?= null,
    val updatedAt: LocalDateTime?= null,
) {

    companion object {
        @JvmStatic
        fun deleteMedia(media: Media) : Media {
            return Media(
                id = media.id,
                fileUrl = media.fileUrl,
                fileType = media.fileType,
                fileSize = media.fileSize,
                userId = media.userId,
                serverName = media.serverName,
                originalName = media.originalName,
                extension = media.extension,
                context = media.context,
                referenceId = media.referenceId,
                isDeleted = true,
                createdAt = media.createdAt,
                updatedAt = media.updatedAt,
            )
        }
    }
}