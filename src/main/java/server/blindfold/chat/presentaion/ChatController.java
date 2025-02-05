package server.blindfold.chat.presentaion;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.blindfold.chat.application.ChatService;
import server.blindfold.chat.dto.module.ChatRoom;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }
}

