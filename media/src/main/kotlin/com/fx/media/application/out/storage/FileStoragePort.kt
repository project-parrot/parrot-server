package com.fx.media.application.out.storage

import com.fx.media.adapter.out.storage.dto.FileStoreCommand
import com.fx.media.domain.Media

interface FileStoragePort {

    suspend fun store(fileStoreCommand: FileStoreCommand) : Media

}