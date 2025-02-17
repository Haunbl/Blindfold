package server.blindfold.room.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRoomMemberRequestDto {
    private String roomId;
    private Long memberId;
}
