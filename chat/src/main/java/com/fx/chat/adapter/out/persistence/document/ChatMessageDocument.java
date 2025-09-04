package com.fx.chat.adapter.out.persistence.document;

import com.fx.chat.domain.ChatMessage;
import com.fx.chat.domain.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "chat_message")
@SuperBuilder
@NoArgsConstructor
public class ChatMessageDocument extends BaseDocument{

    private String chatRoomId; // FK

    private Long senderId;

    private String content;

    private MessageType type;

    public static ChatMessageDocument from(ChatMessage chatMessage) {
        return ChatMessageDocument.builder()
            .id(chatMessage.getId())
            .chatRoomId(chatMessage.getChatRoomId())
            .senderId(chatMessage.getSenderId())
            .content(chatMessage.getContent())
            .type(chatMessage.getType())
            .build();
    }

    public ChatMessage toDomain() {
        return new ChatMessage(
            this.id,
            this.chatRoomId,
            this.senderId,
            this.content,
            this.type,
            this.createdAt,
            this.updatedAt
        );

    }
}
