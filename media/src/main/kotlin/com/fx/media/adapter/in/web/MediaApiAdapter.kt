package com.fx.media.adapter.`in`.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.global.resolver.AuthUser
import com.fx.media.adapter.`in`.web.dto.Context
import com.fx.media.adapter.`in`.web.dto.MediaUploadResponse
import com.fx.media.application.`in`.MediaCommandUseCase
import com.fx.media.application.`in`.MediaQueryUseCase
import com.fx.media.application.`in`.dto.MediaUploadCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@WebInputAdapter
@RequestMapping("/api/v1/media")
class MediaApiAdapter (
    private val mediaCommandUseCase: MediaCommandUseCase,
    private val mediaQueryUseCase: MediaQueryUseCase
) {
    @PostMapping
    fun uploadFile(
        @RequestParam("files") files: List<MultipartFile>,
        @RequestParam context: Context,
        @AuthenticatedUser authUser: AuthUser
    ) : ResponseEntity<Api<List<MediaUploadResponse>>> {

        val mediaResponses = files.map { mediaCommandUseCase.uploadFile(MediaUploadCommand(it, context, authUser.userId)) }

        return Api.OK(mediaResponses.map { MediaUploadResponse.from(it) })
    }

}