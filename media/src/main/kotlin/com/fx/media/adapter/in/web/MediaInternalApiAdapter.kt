package com.fx.media.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.media.adapter.`in`.web.dto.MediaUrlResponse
import com.fx.media.application.`in`.MediaQueryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@WebInputAdapter
@RequestMapping("/internal")
class MediaInternalApiAdapter(
    private val mediaQueryUseCase: MediaQueryUseCase
) {
    @GetMapping("/url")
    fun getMediaUrl(
        @RequestParam mediaIds: List<Long>
    ) : ResponseEntity<List<MediaUrlResponse>> {

        return ResponseEntity.ok(mediaQueryUseCase.getUrl(mediaIds).map { MediaUrlResponse( it.id!!, it.fileUrl )})
    }
}