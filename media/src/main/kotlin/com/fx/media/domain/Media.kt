package com.fx.media.domain

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
    val isDeleted: Boolean = false,
    val createdAt: LocalDateTime?= null,
    val updatedAt: LocalDateTime?= null,
) {
}