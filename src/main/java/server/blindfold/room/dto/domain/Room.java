package server.blindfold.room.dto.domain;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import server.blindfold.member.dto.MemberModule;
import server.blindfold.member.dto.vo.MemberType;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Room {

    private final static Integer MAX_PLAYER = 4;
    private final static Integer MIN_PLAYER = 2;

    private MemberModule[] room;
    private final String roomId = String.valueOf(UUID.randomUUID());
    private MemberModule master;

    public Room(MemberModule master) {
        this.master = master;
    }

    @PostConstruct
    public void init() {
        room = new MemberModule[4];
    }

    /**
     * 멀티 모드 2명~4명 이하인지 확인하는 메서드
     *
     * @param room
     * @return 게임의 인원 확인
     */
    public boolean isReady(Room room) {
        int count = (int) Arrays.stream(room.getRoom())
                .filter(Objects::nonNull)
                .count();

        return MIN_PLAYER <= count && count <= MAX_PLAYER;
    }

    /**
     * 방장이 1명만 있는지 확인하는 메서드
     *
     * @param room
     * @return true -> 방장이 한명이다 / false -> 방장이 여러명이다. 오류
     */
    public boolean isMasterOne(Room room) {
        int count = (int) Arrays.stream(room.getRoom())
                .filter((m) -> m.getMemberType().equals(MemberType.MASTER)).count();

        return count == 1;
    }

    /**
     * 게임 룸 생성(생성자가 자동으로 방장이 됨) 메서드
     *
     * @param memberModule
     * @return Room
     */
    public Room createRoom(MemberModule memberModule) {
        return new Room(memberModule);
    }

    /**
     * 게임 방에 친구 초대
     * @param member
     */
    public void addMember(MemberModule member) {
        for (int i = 0; i < MAX_PLAYER; i++) {
            if(room[i] == null){
                room[i] = member;
                break;
            }
        }
    }

    /**
     * 방장이 다른 멤버에게 방장 권한을 양도하는 메서드
     * @param master
     * @param targetMember
     */
    public void giveMasterRule(MemberModule master,MemberModule targetMember){
        if(master.getMemberType().equals(MemberType.MASTER)){
            master.setMemberType(MemberType.GUEST);
            targetMember.setMemberType(MemberType.MASTER);
        }
    }
}