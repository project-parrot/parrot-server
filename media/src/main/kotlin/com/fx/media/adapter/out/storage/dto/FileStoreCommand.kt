package com.fx.media.adapter.out.storage.dto

import com.fx.global.dto.Context
import org.springframework.web.multipart.MultipartFile

data class FileStoreCommand(
    val file: MultipartFile,
    val context: Context,
    val userId: Long?= null
) {
    companion object {
        fun storeFile(file: MultipartFile, context: Context, userId: Long?= null): FileStoreCommand {
            return FileStoreCommand(
                file = file,
                context = context,
                userId = userId
            )
        }
    }
}