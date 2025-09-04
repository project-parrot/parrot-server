package com.fx.chat.adapter.out.persistence.document;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@SuperBuilder
@NoArgsConstructor
public class BaseDocument {

    @Id
    @Builder.Default
    protected Long id = null;

    @CreatedDate
    @Builder.Default
    protected LocalDateTime createdAt = null; // @Id 값을 미리 할당한 경우 createdAt 생성 불가

    @LastModifiedDate
    @Builder.Default
    protected LocalDateTime updatedAt = null;

}
