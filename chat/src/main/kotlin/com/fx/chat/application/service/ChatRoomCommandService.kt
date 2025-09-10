package com.fx.chat.application.service

import com.fx.chat.application.port.`in`.ChatRoomCommandUseCase
import com.fx.chat.application.port.out.ChatRoomPersistencePort
import com.fx.chat.application.port.out.ChatRoomUserPersistencePort
import com.fx.chat.domain.ChatRoom
import com.fx.chat.domain.ChatRoomType
import com.fx.chat.domain.ChatRoomUser
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class ChatRoomCommandService(
    private val chatRoomPersistencePort: ChatRoomPersistencePort,
    private val chatRoomUserPersistencePort: ChatRoomUserPersistencePort,
//    private val userWebPort: UserWebPort
) : ChatRoomCommandUseCase{

    override suspend fun createOrEnterChatRoom(
        requesterId: Long, chatRoomId: String?, targetUserIds: List<Long>?
    ): ChatRoom = coroutineScope {

        // 1. chatRoomId가 있으면 기존 채팅방 조회
        if (!chatRoomId.isNullOrEmpty()) {
            return@coroutineScope chatRoomPersistencePort.findById(chatRoomId)
                .awaitSingleOrNull()
                ?: throw IllegalArgumentException("존재하지 않는 채팅방입니다.")
        }

        // 2. 없으면 새 채팅방 생성
        if (targetUserIds.isNullOrEmpty()) {
            throw IllegalArgumentException("채팅방이 없으면 targetUserIds가 필요합니다.")
        }

        // 3. 자기 자신 포함
        val participants = (targetUserIds + requesterId).toSet()
        if (participants.size < 2) {
            throw IllegalArgumentException("채팅방을 만들려면 다른 사용자가 필요합니다.")
        }

        // 4. 유효 사용자 체크
//        val invalidUserExists = userWebPort.existsUser(targetUserIds)
//        if (invalidUserExists) {
//            throw RuntimeException("존재하지 않는 사용자가 포함되어 있습니다.")
//        }

        // 5. 채팅방 저장
        val newRoom =
            if (participants.size == 2) ChatRoom.createRoom(ChatRoomType.DIRECT)
            else ChatRoom.createRoom(ChatRoomType.GROUP)

        val savedRoom = chatRoomPersistencePort.saveChatRoom(newRoom)
            .awaitSingle()

        // 6. 참가자 등록
        val chatRoomUsers = ChatRoomUser.createChatRoomUser(savedRoom.id!!, participants)
        chatRoomUserPersistencePort.saveChatRoomUsers(chatRoomUsers)
            .awaitSingle()

        return@coroutineScope savedRoom
    }


}