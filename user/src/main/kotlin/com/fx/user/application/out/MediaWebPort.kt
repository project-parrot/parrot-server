package com.fx.user.application.out

import com.fx.post.adapter.out.web.impl.dto.MediaUrlCommand

interface MediaWebPort {

    fun getUrl(mediaIds: List<Long>): List<MediaUrlCommand>?

}