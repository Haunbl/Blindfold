package server.blindfold.member.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import server.blindfold.member.dto.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberBySteamId(String steamId);
}
