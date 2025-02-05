package server.blindfold.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void createMember(CreateMemberRequestDto request){
        memberRepository.save(
                Member.form(
                        MemberModule.createForm(request)
                )
        );
    }

    @Transactional(readOnly = true)
    public FindMemberByIdResponse findMemberById(FindMemberByIdRequestDto request){
       var member = memberRepository.findMemberBySteamId(request.getMemberId())
               .orElseThrow(NullPointerException::new);

       return FindMemberByIdResponse.form(MemberModule.form(member));
    }

    public void setMasterType(MemberModule memberModule){
        var member = memberRepository.findById(memberModule.getMemberId())
                .orElseThrow(NullPointerException::new);
        member.setMasterMemberType();
    }

    public void setGuestType(MemberModule memberModule){
        var member = memberRepository.findById(memberModule.getMemberId())
                .orElseThrow(NullPointerException::new);
        member.setGuestMemberType();
    }

}
