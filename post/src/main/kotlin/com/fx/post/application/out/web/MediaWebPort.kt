package com.fx.post.application.out.web

import com.fx.post.adapter.out.web.impl.dto.MediaUrlCommand

interface MediaWebPort {

    fun getUrl(mediaIds: List<Long>): List<MediaUrlCommand>?

}