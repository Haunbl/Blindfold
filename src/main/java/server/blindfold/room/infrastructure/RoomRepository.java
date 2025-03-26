package server.blindfold.room.infrastructure;

import org.springframework.stereotype.Repository;
import server.blindfold.room.dto.domain.Room;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RoomRepository {

    private final Map<String, Room> roomMap = new HashMap<>();

    public Room save (Room room){
        return roomMap.put(room.getRoomId(), room);
    }

    public Room update(Room room){
        return roomMap.put(room.getRoomId(), room);
    }

    public Room findRoom(String roomId){
        return roomMap.get(roomId);
    }

    public void deleteRoom(String roomId){
        roomMap.remove(roomId);
    }

}
