package server.blindfold.member.dto.entity;

import jakarta.persistence.*;
import lombok.*;
import server.blindfold.member.dto.MemberModule;
import server.blindfold.member.dto.vo.MemberType;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "steam_id")
    private String steamId;

    @Column(name = "member_type")
    private MemberType memberType = MemberType.GUEST;

    public static Member form(MemberModule memberModule){
        return Member.builder()
                .userName(memberModule.getMemberName())
                .steamId(memberModule.getSteamId())
                .build();
    }

    public void setMasterMemberType(){
        memberType = MemberType.MASTER;
    }

    public void setGuestMemberType(){
        memberType = MemberType.GUEST;
    }
}