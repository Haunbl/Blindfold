package server.blindfold.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import server.blindfold.member.dto.MemberModule;

@Getter
@Setter
@Builder
public class FindMemberByIdResponse {
    private Long id;
    private String memberName;
    private String steamId;
    private String memberType;

    public static FindMemberByIdResponse form(MemberModule memberModule){
        return FindMemberByIdResponse.builder()
                .id(memberModule.getMemberId())
                .memberName(memberModule.getMemberName())
                .steamId(memberModule.getSteamId())
                .memberType(memberModule.getMemberType().toString())
                .build();
    }
}
