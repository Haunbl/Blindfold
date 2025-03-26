package server.blindfold.member.dto.entity;

import jakarta.persistence.*;
import lombok.*;
import server.blindfold.member.dto.MemberModule;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "steam_id")
    private String steamId;


    public static Member form(MemberModule memberModule){
        return Member.builder()
                .userName(memberModule.getMemberName())
                .userCode(memberModule.getMemberCode())
                .steamId(memberModule.getSteamId())
                .build();
    }
}