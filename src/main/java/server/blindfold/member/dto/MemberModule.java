package server.blindfold.member.dto;

import lombok.*;
import server.blindfold.member.dto.entity.Member;
import server.blindfold.member.dto.request.CreateMemberRequestDto;
import server.blindfold.member.dto.vo.MemberType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberModule {
    private Long memberId;
    private String memberName;
    private String memberCode;
    private String steamId;
    private MemberType memberType;

    public static MemberModule createForm(CreateMemberRequestDto request){
        return MemberModule.builder()
                .memberName(request.getMemberName())
                .steamId(request.getSteamId())
                .memberCode(UUID.randomUUID().toString())
                .build();
    }

    public static MemberModule form(Member member){
        return MemberModule.builder()
                .memberId(member.getId())
                .memberName(member.getUserName())
                .memberCode(member.getUserCode())
                .steamId(member.getSteamId())
                .memberType(MemberType.GUEST)
                .build();
    }


    public void convertMemberType(){
        if(memberType.equals(MemberType.GUEST)){
            this.memberType = MemberType.MASTER;
        }
        else if(memberType.equals(MemberType.MASTER)){
            this.memberType = MemberType.GUEST;
        }
    }

}