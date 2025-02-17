package server.blindfold.room.application;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.blindfold.member.application.MemberService;
import server.blindfold.member.dto.MemberModule;
import server.blindfold.room.dto.domain.Room;
import server.blindfold.room.dto.moduel.RoomModule;
import server.blindfold.room.dto.request.AddRoomMemberRequestDto;
import server.blindfold.room.dto.request.CreateRoomRequestDto;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoomService {
    private Map<String, Room> roomMap;
    private final MemberService memberService;

    @PostConstruct
    private void init() {
         this.roomMap = new HashMap<>();
    }

    /**
     * 게임 방 생성
     *
     * @param createRoomRequestDto
     * @return RoomModule
     */
    public RoomModule createRoom(CreateRoomRequestDto createRoomRequestDto){
        var room = new Room(memberService.findMemberById(
                createRoomRequestDto.getMemberId()));
        roomMap.put(room.getRoomId(), room);

        return RoomModule.form(room);
    }

    /**
     * 게임 인원 추가
     *
     * @param addRoomMemberRequestDto
     * @return
     */
    public RoomModule addMember(AddRoomMemberRequestDto addRoomMemberRequestDto){
        var room = roomMap.get(
                addRoomMemberRequestDto.getRoomId());
        room.addMember(
                memberService.findMemberById(
                        addRoomMemberRequestDto.getMemberId()));

        return RoomModule.form(room);
    }
}