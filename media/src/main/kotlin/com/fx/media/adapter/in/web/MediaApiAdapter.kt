package com.fx.media.adapter.`in`.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.global.dto.Context
import com.fx.global.resolver.AuthUser
import com.fx.media.adapter.`in`.web.dto.MediaGetResponse
import com.fx.media.adapter.`in`.web.dto.MediaUploadResponse
import com.fx.media.application.`in`.MediaCommandUseCase
import com.fx.media.application.`in`.MediaQueryUseCase
import com.fx.media.application.`in`.dto.MediaUploadCommand
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@WebInputAdapter
@RequestMapping("/api/v1/media")
class MediaApiAdapter(
    private val mediaCommandUseCase: MediaCommandUseCase,
    private val mediaQueryUseCase: MediaQueryUseCase,
) {
    @Operation(summary = "미디어 업로드",
        description = "context 종류 : POST, CHAT, PROFILE" +
                " 요청 예시 : /api/v1/media?context=PROFILE + form-data로 files 파라미터에 파일들 업로드")
    @PostMapping
    suspend fun uploadFile(
        @RequestParam("files") files: List<MultipartFile>,
        @RequestParam context: Context,
        @AuthenticatedUser authUser: AuthUser
    ) : ResponseEntity<Api<List<MediaUploadResponse>>> {
        val medias = mediaCommandUseCase.uploadFile(MediaUploadCommand(files, context, authUser.userId))
        return Api.OK(medias.map { MediaUploadResponse.from(it) })
    }

    @Operation(summary = "미디어 조회")
    @GetMapping("/{mediaId}")
    fun getFile(
        @PathVariable mediaId: Long
    ) : ResponseEntity<Api<MediaGetResponse>> {
        val media = mediaQueryUseCase.getFile(mediaId)
        return Api.OK(MediaGetResponse.from(media))
    }

    @Operation(summary = "미디어 삭제")
    @DeleteMapping("/{mediaId}")
    fun deleteFile(
        @PathVariable mediaId: Long,
        @AuthenticatedUser authUser: AuthUser
    ) : ResponseEntity<Api<Unit>> {
        mediaCommandUseCase.deleteFile(mediaId, authUser.userId, authUser.role)
        return Api.OK(Unit)
    }


    @GetMapping
    fun getContextMedias(
        @RequestParam context: Context,
        @RequestParam referenceId: Long
    ) : ResponseEntity<Api<List<MediaGetResponse>>> =
        Api.OK(
            mediaQueryUseCase.getFiles(context, referenceId)
                .map { MediaGetResponse.from(it) }
        )

}