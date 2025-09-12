package com.fx.chat.adapter.out.persistence.repository;

import com.fx.chat.adapter.out.persistence.document.ChatRoomUserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ChatRoomUserMongoRepository extends ReactiveMongoRepository<ChatRoomUserDocument, String> {

}
