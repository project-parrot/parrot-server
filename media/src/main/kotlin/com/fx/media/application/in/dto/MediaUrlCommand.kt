package com.fx.media.application.`in`.dto

import com.fx.media.adapter.`in`.web.dto.MediaInfo

data class MediaUrlCommand(
    val referenceId: Long,
    val mediaInfos: List<MediaInfo>?
)
