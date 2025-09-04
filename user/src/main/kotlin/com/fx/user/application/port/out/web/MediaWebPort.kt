package com.fx.user.application.port.out.web

import com.fx.global.dto.Context
import com.fx.user.adapter.out.web.impl.dto.MediaUrlCommand

interface MediaWebPort {

    fun getUrls(context: Context, referenceIds: List<Long>): List<MediaUrlCommand>?

}