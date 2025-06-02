package server.blindfold.chat.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import server.blindfold.chat.dto.module.ChatMessage;
import server.blindfold.chat.dto.module.ChatRoom;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatServiceTest {

    private ChatService chatService;
    private ObjectMapper objectMapperMock;

    @BeforeEach
    void setUp() {
        // ObjectMapper를 Mock 객체로 생성
        objectMapperMock = mock(ObjectMapper.class);

        // ChatService 생성
        chatService = new ChatService(objectMapperMock);
    }

    @Test
    @DisplayName("모든 채팅방을 조회하면 생성된 채팅방 리스트가 반환된다")
    void findAllRoom_ReturnsAllRooms() {
        // Given: 채팅방 생성
        chatService.createRoom("Room1");
        chatService.createRoom("Room2");

        // When: 모든 채팅방 조회
        List<ChatRoom> chatRooms = chatService.findAllRoom();

        // Then: 생성된 채팅방이 반환되어야 한다
        assertThat(chatRooms).hasSize(2);
        assertThat(chatRooms).extracting("name").contains("Room1", "Room2");
    }

    @Test
    @DisplayName("유효한 roomId로 채팅방 조회하면 해당 채팅방이 반환된다")
    void findRoomById_ReturnsRoom() {
        // Given: Room 생성
        ChatRoom createdRoom = chatService.createRoom("ValidRoom");

        // When: Room을 id로 조회
        ChatRoom foundRoom = chatService.findRoomById(createdRoom.getRoomId());

        // Then: Room이 반환되어야 한다
        assertThat(foundRoom).isNotNull();
        assertThat(foundRoom).isEqualTo(createdRoom);
    }

    @Test
    @DisplayName("존재하지 않는 roomId로 조회하면 null이 반환된다")
    void findRoomById_ReturnsNull() {
        // When: 존재하지 않는 roomId로 Room 조회
        ChatRoom foundRoom = chatService.findRoomById("invalid-room-id");

        // Then: null이 반환되어야 한다
        assertThat(foundRoom).isNull();
    }

    @Test
    @DisplayName("채팅방을 생성하면 chatRooms에 추가된다")
    void createRoom_Success() {
        // When: 채팅방 생성
        ChatRoom createdRoom = chatService.createRoom("New Chat Room");

        // Then: 생성된 채팅방이 반환되고, chatRooms에 추가되어야 한다
        assertThat(chatService.findRoomById(createdRoom.getRoomId())).isNotNull();
        assertThat(createdRoom.getName()).isEqualTo("New Chat Room");
    }

    @Test
    @DisplayName("sendMessage 호출 시 WebSocketSession에 올바른 payload가 전달된다")
    void sendMessage_WithValidPayload() throws Exception {
        // Given: Mock WebSocketSession과 메시지
        WebSocketSession sessionMock = mock(WebSocketSession.class);
        ChatMessage chatMessage = ChatMessage.builder()
            .type(ChatMessage.MessageType.ENTER)
            .roomId("test-room")
            .sender("User1")
            .message("User1님이 입장했습니다.")
            .build();

        // ObjectMapper Mock 설정
        when(objectMapperMock.writeValueAsString(chatMessage))
            .thenReturn("{\"type\":\"ENTER\",\"roomId\":\"test-room\",\"sender\":\"User1\",\"message\":\"User1님이 입장했습니다.\"}");

        // When: sendMessage 호출
        chatService.sendMessage(sessionMock, chatMessage);

        // Then: 전달된 TextMessage의 payload 확인
        ArgumentCaptor<TextMessage> captor = ArgumentCaptor.forClass(TextMessage.class);
        verify(sessionMock).sendMessage(captor.capture());
        String actualPayload = captor.getValue().getPayload();
        assertThat(actualPayload).isEqualTo("{\"type\":\"ENTER\",\"roomId\":\"test-room\",\"sender\":\"User1\",\"message\":\"User1님이 입장했습니다.\"}");

    }

    @Test
    @DisplayName("브로드캐스트 시 모든 WebSocketSession에서 sendMessage가 호출된다")
    void broadcast_CallsSendMessageOnAllSessions() throws Exception {
        // Given: WebSocketSession Mock 및 ChatRoom 설정
        WebSocketSession sessionMock1 = mock(WebSocketSession.class);
        WebSocketSession sessionMock2 = mock(WebSocketSession.class);
        ChatRoom chatRoom = chatService.createRoom("Test Room");
        chatRoom.getSessions().add(sessionMock1);
        chatRoom.getSessions().add(sessionMock2);

        ChatMessage chatMessage = ChatMessage.builder()
            .type(ChatMessage.MessageType.TALK)
            .roomId(chatRoom.getRoomId())
            .message("Broadcast Message")
            .build();

        // When: broadcast 호출
        when(objectMapperMock.writeValueAsString(chatMessage))
            .thenReturn("{\"type\":\"ENTER\",\"roomId\":\"Test Room\",\"sender\":\"User1\",\"message\":\"Broadcast Message\"}");
        chatService.broadcast(chatMessage, chatRoom.getRoomId());

        // Then: 모든 세션의 sendMessage 메서드가 1번씩 호출되었는지 확인
        verify(sessionMock1, times(1)).sendMessage(any(TextMessage.class));
        verify(sessionMock2, times(1)).sendMessage(any(TextMessage.class));
    }

    @Test
    @DisplayName("sendMessage 호출 시 WebSocketSession에 메시지가 정상적으로 전송된다")
    void sendMessage_SendsTextMessage() throws Exception {
        // Given: WebSocketSession 및 메시지
        WebSocketSession sessionMock = mock(WebSocketSession.class);
        ChatMessage chatMessage = ChatMessage.builder()
            .type(ChatMessage.MessageType.TALK)
            .roomId("test-room")
            .sender("User1")
            .message("Test Message")
            .build();

        // 직렬화가 성공적으로 동작하도록 Mock 설정
        when(objectMapperMock.writeValueAsString(chatMessage))
            .thenReturn("{\"type\":\"TALK\",\"roomId\":\"test-room\",\"sender\":\"User1\",\"message\":\"Test Message\"}");

        // When: sendMessage 호출
        chatService.sendMessage(sessionMock, chatMessage);

        // Then: 정상적으로 TextMessage가 전송되었는지 확인
        ArgumentCaptor<TextMessage> captor = ArgumentCaptor.forClass(TextMessage.class);
        verify(sessionMock, times(1)).sendMessage(captor.capture());
        String sentMessage = captor.getValue().getPayload();
        assertThat(sentMessage).isEqualTo("{\"type\":\"TALK\",\"roomId\":\"test-room\",\"sender\":\"User1\",\"message\":\"Test Message\"}");
    }

    @Test
    @DisplayName("ObjectMapper에서 직렬화 예외가 발생해도 메시지 전송이 중단되지 않는다")
    void sendMessage_HandlesSerializationException() throws Exception {
        // Given
        WebSocketSession sessionMock = mock(WebSocketSession.class);
        ChatMessage chatMessage = ChatMessage.builder()
            .type(ChatMessage.MessageType.TALK)
            .roomId("test-room")
            .sender("User1")
            .message("Serialization Test")
            .build();

        doThrow(new JsonProcessingException("Serialization failed") {
        })
            .when(objectMapperMock).writeValueAsString(chatMessage);

        // When & Then
        assertThatCode(() -> chatService.sendMessage(sessionMock, chatMessage))
            .doesNotThrowAnyException();

        // 로그 검증
        verify(objectMapperMock).writeValueAsString(chatMessage); // 직렬화 시도는 수행되었는지 검증
        verify(sessionMock, never()).sendMessage(any()); // WebSocket 메시지는 전송되지 않음
    }
}