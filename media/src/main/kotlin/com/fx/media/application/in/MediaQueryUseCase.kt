package com.fx.media.application.`in`

import com.fx.media.domain.Media

interface MediaQueryUseCase {

    fun getFile(mediaId: Long) : Media

}