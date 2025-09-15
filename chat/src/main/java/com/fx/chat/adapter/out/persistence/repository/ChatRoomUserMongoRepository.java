package com.fx.chat.adapter.out.persistence.repository;

import com.fx.chat.adapter.out.persistence.document.ChatRoomUserDocument;
import com.fx.chat.domain.ChatRoomUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ChatRoomUserMongoRepository extends ReactiveMongoRepository<ChatRoomUserDocument, String> {

    Mono<ChatRoomUserDocument> findByChatRoomIdAndUserId(String chatRoomId, Long userId);

}
