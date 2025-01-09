package server.blindfold.member.presentaion;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import server.blindfold.member.application.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
}
