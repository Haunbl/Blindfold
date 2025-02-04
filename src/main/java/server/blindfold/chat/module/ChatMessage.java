package server.blindfold.chat.module;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;

    public enum MessageType{
        ENTER, TALK
    }
}

