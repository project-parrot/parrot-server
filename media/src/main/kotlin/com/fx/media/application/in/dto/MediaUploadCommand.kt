package com.fx.media.application.`in`.dto

import com.fx.media.adapter.`in`.web.dto.Context
import org.springframework.web.multipart.MultipartFile

data class MediaUploadCommand(
    val files: List<MultipartFile>,
    val context: Context,
    val userId: Long
)
