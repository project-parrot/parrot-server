package com.fx.chat.adapter.out.persistence.repository;

import com.fx.chat.adapter.out.persistence.document.ChatMessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ChatMessageMongoRepository extends ReactiveMongoRepository<ChatMessageDocument, String> {

}
