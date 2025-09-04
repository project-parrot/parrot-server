package com.fx.chat.adapter.out.persistence.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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

}
