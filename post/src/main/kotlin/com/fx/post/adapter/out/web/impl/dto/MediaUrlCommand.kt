package com.fx.post.adapter.out.web.impl.dto

data class MediaUrlCommand(
    val referenceId: Long,
    val mediaInfos: List<MediaInfo>?
)
