package server.blindfold.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.blindfold.member.dto.MemberModule;
import server.blindfold.member.dto.entity.Member;
import server.blindfold.member.dto.request.CreateMemberRequestDto;

import server.blindfold.member.dto.request.FindMemberByIdRequestDto;
import server.blindfold.member.dto.response.FindMemberByIdResponse;
import server.blindfold.member.infrastructure.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 최초 입장한 이용자에 대한 생성 메서드
     * - member code 생성
     *
     * @param request
     */
    public void createMember(CreateMemberRequestDto request){
        memberRepository.save(
                Member.form(
                        MemberModule.createForm(request)
                )
        );
    }

    /**
     * steamID 기반 죄회 기능
     *
     * @param request
     * @return
     */

    public FindMemberByIdResponse findMemberById(FindMemberByIdRequestDto request){
       var member = memberRepository.findMemberBySteamId(
               request.getMemberId())
               .orElseThrow(NullPointerException::new);

       return FindMemberByIdResponse.form(MemberModule.form(member));
    }


    /**
     * 생성 ID 로 조회하는 메서드
     *
     * @param memberId
     * @return
     */
    public MemberModule findMemberById(Long memberId){
        var member = memberRepository.findById(memberId)
                .orElseThrow(NullPointerException::new);

        return MemberModule.form(member);
    }
}