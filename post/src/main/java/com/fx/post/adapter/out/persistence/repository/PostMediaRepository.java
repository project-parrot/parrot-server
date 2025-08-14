package com.fx.post.adapter.out.persistence.repository;

import com.fx.post.adapter.out.persistence.entity.PostMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostMediaRepository extends JpaRepository<PostMediaEntity, Long> {

    List<PostMediaEntity> findByPostId(Long postId);

    void deleteByPostIdAndMediaId(Long postId, Long mediaId);

}
