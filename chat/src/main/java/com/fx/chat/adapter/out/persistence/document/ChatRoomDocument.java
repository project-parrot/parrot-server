package com.fx.chat.adapter.out.persistence.document;

import com.fx.chat.domain.ChatRoom;
import com.fx.chat.domain.ChatRoomType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "chat_room")
@SuperBuilder
@NoArgsConstructor
public class ChatRoomDocument extends BaseDocument {

    private String lastMessageId;

    private ChatRoomType type;

    public static ChatRoomDocument from(ChatRoom chatRoom) {
        return ChatRoomDocument.builder()
            .lastMessageId(chatRoom.getLastMessageId())
            .type(chatRoom.getType())
            .createdAt(chatRoom.getCreatedAt())
            .updatedAt(chatRoom.getUpdatedAt())
            .build();
    }
}
