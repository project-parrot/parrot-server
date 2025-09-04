package com.fx.chat.adapter.out.persistence.repository;

import com.fx.chat.adapter.out.persistence.document.ChatRoomUserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomUserRepository extends MongoRepository<ChatRoomUserDocument, String> {

}
