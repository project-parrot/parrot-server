package com.fx.media.adapter.out.persistence.entity;

import com.fx.global.dto.FileType;
import com.fx.media.domain.Media;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "media")
@SuperBuilder
@NoArgsConstructor
public class MediaEntity extends BaseEntity {

    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private Long fileSize;

    private Long userId;

    private String serverName;

    private String originalName;

    private String extension;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isDeleted;

    public static MediaEntity from(Media media) {
        return MediaEntity.builder()
                .id(media.getId())
                .fileUrl(media.getFileUrl())
                .fileType(media.getFileType())
                .fileSize(media.getFileSize())
                .userId(media.getUserId())
                .serverName(media.getServerName())
                .originalName(media.getOriginalName())
                .extension(media.getExtension())
                .isDeleted(media.isDeleted())
                .build();
    }

    public Media toDomain() {
        return new Media(
                this.id,
                this.fileUrl,
                this.fileType,
                this.fileSize,
                this.userId,
                this.serverName,
                this.originalName,
                this.extension,
                this.isDeleted,
                this.createdAt,
                this.updatedAt
        );
    }
}
