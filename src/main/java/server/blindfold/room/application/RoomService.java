package server.blindfold.room.application;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import server.blindfold.room.dto.domain.Room;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class RoomService {
    private Map<String, Room> roomMap;

    @PostConstruct
    private void init() {
         this.roomMap = new HashMap<>();
    }


}
