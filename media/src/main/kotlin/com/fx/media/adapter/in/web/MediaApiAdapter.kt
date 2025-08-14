package com.fx.media.adapter.`in`.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.global.resolver.AuthUser
import com.fx.media.adapter.`in`.web.dto.Context
import com.fx.media.adapter.`in`.web.dto.MediaGetResponse
import com.fx.media.adapter.`in`.web.dto.MediaUploadResponse
import com.fx.media.adapter.out.storage.dto.FileStoreCommand
import com.fx.media.application.`in`.MediaCommandUseCase
import com.fx.media.application.`in`.MediaQueryUseCase
import com.fx.media.application.`in`.dto.MediaUploadCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@WebInputAdapter
@RequestMapping("/api/v1/media")
class MediaApiAdapter (
    private val mediaCommandUseCase: MediaCommandUseCase,
    private val mediaQueryUseCase: MediaQueryUseCase
) {
    @PostMapping
    suspend fun uploadFile(
        @RequestParam("files") files: List<MultipartFile>,
        @RequestParam context: Context,
        @AuthenticatedUser authUser: AuthUser
    ) : ResponseEntity<Api<List<MediaUploadResponse>>> {

        val medias = mediaCommandUseCase.uploadFile(MediaUploadCommand(files, context, authUser.userId))
        return Api.OK(medias.map { MediaUploadResponse.from(it) })
    }

    @GetMapping("/{mediaId}")
    fun getFile(
        @PathVariable mediaId: Long
    ) : ResponseEntity<Api<MediaGetResponse>> {

        val media = mediaQueryUseCase.getFile(mediaId)
        return Api.OK(MediaGetResponse.from(media))
    }

    @DeleteMapping("/{mediaId}")
    fun deleteFile(
        @PathVariable mediaId: Long,
        @AuthenticatedUser authUser: AuthUser
    ) : ResponseEntity<Api<Unit>> {

        mediaCommandUseCase.deleteFile(mediaId, authUser.userId, authUser.role)
        return Api.OK(Unit)

    }


}