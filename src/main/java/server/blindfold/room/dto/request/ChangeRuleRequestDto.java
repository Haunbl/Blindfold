package server.blindfold.room.dto.request;

import lombok.Getter;

@Getter
public class ChangeRuleRequestDto {
    private String roomId;
    private String masterCode;
    private String targetMemberCode;
}
