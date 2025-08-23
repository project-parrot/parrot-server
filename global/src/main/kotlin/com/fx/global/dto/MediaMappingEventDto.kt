package com.fx.global.dto

data class MediaMappingEventDto(
    val context: Context,
    val referenceId: Long?,
    val userId: Long,
    val mediaIds: List<Long>?
)
