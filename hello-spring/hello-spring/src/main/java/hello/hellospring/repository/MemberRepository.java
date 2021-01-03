package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); //저장소에 회원 저장
    Optional<Member> findById(Long id); // Id찾기
    Optional<Member> findByName(String name); // name 찾기
    List<Member> findAll();//모든 값 찾기
}
