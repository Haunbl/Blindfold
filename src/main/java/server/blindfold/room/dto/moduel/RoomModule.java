package server.blindfold.room.dto.moduel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import server.blindfold.member.dto.MemberModule;
import server.blindfold.room.dto.domain.Room;

@Builder
@Getter
@Setter
public class RoomModule {
    private String  roomId;
    private MemberModule[] room;
    private MemberModule master;

    public static RoomModule form(Room room){
        return RoomModule.builder()
                .roomId(room.getRoomId())
                .room(room.getRoom())
                .master(room.getMaster())
                .build();
    }
}