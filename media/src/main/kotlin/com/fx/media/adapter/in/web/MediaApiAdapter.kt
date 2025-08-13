package com.fx.media.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.media.application.`in`.MediaCommandUseCase
import com.fx.media.application.`in`.MediaQueryUseCase
import org.springframework.web.bind.annotation.RequestMapping

@WebInputAdapter
@RequestMapping("/api/v1/media")
class MediaApiAdapter (
    private val mediaCommandUseCase: MediaCommandUseCase,
    private val mediaQueryUseCase: MediaQueryUseCase
) {

}