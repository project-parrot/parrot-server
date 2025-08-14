package com.fx.media.adapter.out.storage.dto

import java.nio.file.Path

data class FileInfo(
    val originalName: String,
    val extension: String,
    val serverName: String,
    val path: Path
)
