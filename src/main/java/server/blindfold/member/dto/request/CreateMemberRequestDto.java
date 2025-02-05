package server.blindfold.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateMemberRequestDto {
    private String memberName;
    private String steamId;
}
