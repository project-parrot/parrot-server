package com.fx.post.application.out.web

import com.fx.global.dto.Context
import com.fx.post.adapter.out.web.impl.dto.MediaUrlCommand

interface MediaWebPort {

    fun getUrls(context: Context, referenceIds: List<Long>): List<MediaUrlCommand>?

}