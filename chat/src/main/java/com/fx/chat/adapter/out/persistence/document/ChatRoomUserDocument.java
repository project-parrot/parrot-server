package com.fx.chat.adapter.out.persistence.document;

import com.fx.chat.domain.ChatRoomUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "chat_room_user")
@SuperBuilder
@NoArgsConstructor
public class ChatRoomUserDocument extends BaseDocument {

    private String chatRoomId;

    private Long userId;

    private String lastReadMessageId;

    // 채팅방 참여일은 BaseDocument 의 createdAt 을 사용한다.

    public static ChatRoomUserDocument from(ChatRoomUser chatRoomUser) {
        return ChatRoomUserDocument.builder()
            .id(chatRoomUser.getId())
            .chatRoomId(chatRoomUser.getChatRoomId())
            .userId(chatRoomUser.getUserId())
            .lastReadMessageId(chatRoomUser.getLastReadMessageId())
            .createdAt(chatRoomUser.getCreatedAt())
            .updatedAt(chatRoomUser.getUpdatedAt())
            .build();
    }

    public ChatRoomUser toDomain() {
        return new ChatRoomUser(
            this.getId(),
            this.getChatRoomId(),
            this.getUserId(),
            this.getLastReadMessageId(),
            this.getUpdatedAt(),
            this.getCreatedAt()
        );
    }

}
