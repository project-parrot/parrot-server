package com.fx.chat.application.service

import com.fx.chat.application.port.`in`.ChatRoomCommandUseCase
import com.fx.chat.application.port.out.ChatRoomPersistencePort
import com.fx.chat.application.port.out.ChatRoomUserPersistencePort
import com.fx.chat.application.port.out.web.UserWebPort
import com.fx.chat.domain.ChatRoom
import com.fx.chat.domain.ChatRoomType
import com.fx.chat.domain.ChatRoomUser
import com.fx.chat.exception.ChatRoomException
import com.fx.chat.exception.errorcode.ChatRoomErrorCode
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service

@Service
class ChatRoomCommandService(
    private val chatRoomPersistencePort: ChatRoomPersistencePort,
    private val chatRoomUserPersistencePort: ChatRoomUserPersistencePort,
    private val userWebPort: UserWebPort
) : ChatRoomCommandUseCase{

    override suspend fun createOrEnterChatRoom(
        requesterId: Long, chatRoomId: String?, targetUserIds: List<Long>?
    ): ChatRoom = coroutineScope {

        // 1. chatRoomId가 있으면 기존 채팅방 조회
        if (!chatRoomId.isNullOrEmpty()) {
            return@coroutineScope chatRoomPersistencePort.findById(chatRoomId)
                .awaitSingleOrNull()
                ?: throw ChatRoomException(ChatRoomErrorCode.CHAT_ROOM_NOT_FOUND)
        }

        // 2. 없으면 새 채팅방 생성
        if (targetUserIds.isNullOrEmpty()) {
            throw ChatRoomException(ChatRoomErrorCode.TARGET_USER_IDS_REQUIRED)
        }

        // 3. 자기 자신 포함
        val participants = (targetUserIds + requesterId).toSet()
        if (participants.size < 2) {
            throw ChatRoomException(ChatRoomErrorCode.PARTICIPANT_NOT_ENOUGH)
        }

        // 4. 유효 사용자 체크
        val usersExist = userWebPort.existsUsers(targetUserIds)
        if (!usersExist) {
            throw ChatRoomException(ChatRoomErrorCode.INVALID_PARTICIPANT)
        }

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