package server.blindfold.member.dto;

import lombok.*;
import server.blindfold.member.dto.entity.Member;
import server.blindfold.member.dto.request.CreateMemberRequestDto;
import server.blindfold.member.dto.vo.MemberType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberModule {
    private Long memberId;
    private String memberName;
    private String steamId;
    private MemberType memberType;

    public static MemberModule createForm(CreateMemberRequestDto request){
        return MemberModule.builder()
                .memberName(request.getMemberName())
                .steamId(request.getSteamId())
                .build();
    }

    public static MemberModule form(Member member){
        return MemberModule.builder()
                .memberId(member.getId())
                .memberName(member.getUserName())
                .steamId(member.getSteamId())
                .memberType(member.getMemberType())
                .build();
    }
}