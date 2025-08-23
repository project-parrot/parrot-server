package com.fx.media.application.`in`

import com.fx.global.dto.Context
import com.fx.media.application.`in`.dto.MediaUrlCommand
import com.fx.media.domain.Media

interface MediaQueryUseCase {

    fun getFile(mediaId: Long) : Media

    fun getUrl(mediaIds: List<Long>) : List<Media>

    fun getUrls(context: Context, referenceIds: List<Long>) : List<MediaUrlCommand>

    fun getFiles(context: Context, referenceId: Long) : List<Media>

}