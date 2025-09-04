package com.fx.chat.adapter.out.persistence.repository;

import com.fx.chat.adapter.out.persistence.document.ChatRoomDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomMongoRepository extends MongoRepository<ChatRoomDocument, String> {

}
