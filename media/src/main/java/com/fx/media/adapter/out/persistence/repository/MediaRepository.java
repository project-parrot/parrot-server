package com.fx.media.adapter.out.persistence.repository;

import com.fx.media.adapter.out.persistence.entity.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<MediaEntity, Long> {

    Optional<MediaEntity> findByIdAndIsDeleted(Long id, boolean isDeleted);

}
