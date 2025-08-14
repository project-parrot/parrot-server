package com.fx.media.adapter.out.storage

import com.fx.global.annotation.hexagonal.FileStorageAdapter
import com.fx.global.dto.FileType
import com.fx.media.adapter.`in`.web.dto.Context
import com.fx.media.adapter.out.storage.dto.FileInfo
import com.fx.media.adapter.out.storage.dto.FileStoreCommand
import com.fx.media.application.`in`.dto.MediaUploadCommand
import com.fx.media.application.out.storage.FileStoragePort
import com.fx.media.domain.Media
import com.fx.media.exception.MediaException
import com.fx.media.exception.errorcode.MediaErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Slf4j
@FileStorageAdapter
class LocalFileStorageAdapter (
    @Value("\${file.upload-dir}") val uploadDir: String,
    @Value("\${file.context-path}") val contextPath: String,
    @Value("\${url.host}") val host: String,
    @Value("\${url.scheme}") val scheme: String,
    @Value("\${url.prefix}") val prefix: String,
) : FileStoragePort {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override suspend fun store(fileStoreCommand: FileStoreCommand): Media = withContext(Dispatchers.IO) {
        createDirectory(uploadDir)

        val fileInfo = createFileInfo(fileStoreCommand.file)
        copyFileToStorage(fileStoreCommand.file, fileInfo.path)
        logUploadStatus(fileInfo.path)

        val fileUrl = createUrl(fileInfo, fileStoreCommand.context)

        createMedia(fileStoreCommand, fileInfo, fileUrl)
    }

    private fun createFileInfo(file: MultipartFile): FileInfo {
        val originalName = file.originalFilename?.let { StringUtils.cleanPath(it) } ?: "unknown"
        val extension = StringUtils.getFilenameExtension(originalName)?.lowercase() ?: ""
        val serverName = UUID.randomUUID().toString()
        val path = if (extension.isBlank()) {
            Paths.get(uploadDir, serverName) // 확장자 없는 경우
        } else {
            Paths.get(uploadDir, "$serverName.$extension")
        }

        return FileInfo(originalName, extension, serverName, path)
    }

    private fun copyFileToStorage(file: MultipartFile, path: Path) {
        try {
            Files.copy(file.inputStream, path, StandardCopyOption.REPLACE_EXISTING)
        } catch (_: Exception) {
            throw MediaException(MediaErrorCode.FILE_UPLOAD_FAILED)
        }
    }

    private fun createDirectory(dirPath: String) {
        val directory = File(dirPath)
        if (!directory.exists() && !directory.mkdirs()) {
            throw MediaException(MediaErrorCode.DIRECTORY_CREATION_FAILED)
        }
    }

    private fun createMedia(fileStoreCommand: FileStoreCommand, fileInfo: FileInfo, fileUrl: String): Media {
        val fileType = when (fileInfo.extension.lowercase()) {
            "png", "jpg", "jpeg", "gif" -> FileType.IMAGE
            "mp4", "mov", "avi" -> FileType.VIDEO
            else -> FileType.FILE
        }

        return Media(
            userId = fileStoreCommand.userId,
            fileUrl = fileUrl,
            fileType = fileType,
            fileSize = fileStoreCommand.file.size,
            serverName = fileInfo.serverName,
            originalName = fileInfo.originalName,
            extension = fileInfo.extension
        )
    }

    private fun createUrl(fileInfo: FileInfo, context: Context): String {
        return UriComponentsBuilder.newInstance()
            .scheme(scheme)
            .host(host)
            .path(prefix + contextPath + fileInfo.path.fileName)
            .toUriString()
    }


    private fun logUploadStatus(filePath: Path) {
        if (Files.exists(filePath)) {
            log.info("Image uploaded successfully to {}", filePath);
        } else {
            log.error("Failed to upload image to {}", filePath);
        }
    }

}