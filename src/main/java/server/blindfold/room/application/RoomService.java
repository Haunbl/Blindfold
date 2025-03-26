    package server.blindfold.room.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.blindfold.member.dto.MemberModule;
import server.blindfold.member.infrastructure.MemberRepository;
import server.blindfold.room.dto.domain.Room;
import server.blindfold.room.dto.moduel.RoomModule;
import server.blindfold.room.dto.request.AddRoomMemberRequestDto;
import server.blindfold.room.dto.request.ChangeRuleRequestDto;
import server.blindfold.room.dto.request.CreateRoomRequestDto;
import server.blindfold.room.infrastructure.RoomRepository;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    /**
     * 게임 방 생성
     *
     * @param createRoomRequestDto
     * @return RoomModule
     */
    @Transactional
    public RoomModule createRoom(CreateRoomRequestDto createRoomRequestDto){
        var room = new Room(
                MemberModule.form(memberRepository.findById(
                createRoomRequestDto.getMemberId()).orElseThrow(NullPointerException::new))
        );
        roomRepository.save(room);

        return RoomModule.form(room);
    }

    /**
     * 게임 인원 추가
     *
     * @param addRoomMemberRequestDto
     * @return
     */
    @Transactional
    public RoomModule addMember(AddRoomMemberRequestDto addRoomMemberRequestDto){
        var room = roomRepository.findRoom(addRoomMemberRequestDto.getRoomId());
        room.addMember(
                MemberModule.form(memberRepository.findById(
                        addRoomMemberRequestDto.getMemberId()).orElseThrow(NullPointerException::new)));

        return RoomModule.form(room);
    }

    /**
     * 게임 마스터 변경
     *
     * @param changeRuleRequestDto
     * @return
     */
    @Transactional
    public RoomModule changeMasterRule(ChangeRuleRequestDto changeRuleRequestDto) {
        var room = roomRepository.findRoom(changeRuleRequestDto.getRoomId());
        var master = MemberModule.form(
                memberRepository.findMemberByUserCode(
                        changeRuleRequestDto.getMasterCode()).orElseThrow(NullPointerException::new));
        var targetMember = MemberModule.form(
                memberRepository.findMemberByUserCode(
                        changeRuleRequestDto.getTargetMemberCode()).orElseThrow(NullPointerException::new));
        room.giveMasterRule(master, targetMember);
        roomRepository.update(room);

        return RoomModule.form(room);
    }
}