package com.fx.chat.adapter.out.persistence.repository;

import com.fx.chat.adapter.out.persistence.document.ChatRoomDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ChatRoomMongoRepository extends ReactiveMongoRepository<ChatRoomDocument, String> {

}
