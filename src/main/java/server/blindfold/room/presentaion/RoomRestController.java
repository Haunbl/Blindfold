package server.blindfold.room.presentaion;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.blindfold.room.application.RoomService;
import server.blindfold.room.dto.request.CreateRoomRequestDto;
import server.blindfold.util.response.ResponseDto;

@RestController
@RequestMapping("/api/V1/room")
@RequiredArgsConstructor
public class RoomRestController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<?> createRoom(CreateRoomRequestDto requestDto){
        var room = roomService.createRoom(requestDto);

        return ResponseDto.ok(room);
    }

    @GetMapping
    public ResponseEntity<?> findRoom(){
        return ResponseDto.ok("good");
    }
}