package com.fx.chat.adapter.out.persistence.document;

import com.fx.chat.domain.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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

}
