package server.blindfold.chat.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import server.blindfold.chat.dto.module.ChatMessage;
import server.blindfold.chat.dto.module.ChatRoom;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private final Map<String, ChatRoom> chatRooms = new LinkedHashMap<>();

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
            .roomId(randomId)
            .name(name)
            .build();
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }

    public void handleActions(WebSocketSession session, ChatMessage chatMessage) {
        var room = chatRooms.get(chatMessage.getRoomId());
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            room.getSessions().add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
            chatRooms.put(room.getRoomId(), room);
        }
        broadcast(chatMessage, room.getRoomId());
    }

    public <T> void broadcast(T message, String roomId) {
        chatRooms.get(roomId).getSessions().parallelStream()
            .forEach(session -> sendMessage(session, message));
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}