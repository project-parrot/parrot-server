package com.fx.media.application.service

import com.fx.global.dto.Context
import com.fx.media.adapter.`in`.web.dto.MediaInfo
import com.fx.media.application.`in`.MediaQueryUseCase
import com.fx.media.application.`in`.dto.MediaUrlCommand
import com.fx.media.application.out.persistence.MediaPersistencePort
import com.fx.media.domain.Media
import com.fx.media.exception.MediaException
import com.fx.media.exception.errorcode.MediaErrorCode
import org.springframework.stereotype.Service

@Service
class MediaQueryService(
    private val mediaPersistencePort: MediaPersistencePort,
) : MediaQueryUseCase {

    override fun getFile(mediaId: Long): Media =
        mediaPersistencePort.findByIdAndIsDeleted(mediaId) ?: throw MediaException(MediaErrorCode.MEDIA_NOT_FOUND)

    override fun getUrls(context: Context, referenceIds: List<Long>): List<MediaUrlCommand> {
        val medias = mediaPersistencePort.findByContextAndReferenceIdInAndIsDeleted(context, referenceIds)

        return referenceIds.map { referenceId ->
            val mediaList = medias.filter { it.referenceId == referenceId }
            MediaUrlCommand(
                referenceId = referenceId,
                mediaInfos = mediaList.map { MediaInfo( it.id, it.fileUrl ) }
            )
        }
    }

    override fun getFiles(context: Context, referenceId: Long): List<Media> =
        mediaPersistencePort.findByContextAndReferenceIdAndIsDeleted(context, referenceId)

}