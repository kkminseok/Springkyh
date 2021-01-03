package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>, MemberRepository{

    //구현체를 자동으로 만들어주고 Spring Bean에 등록함. 가져다 쓰기만하면 된다.
    @Override
    Optional<Member> findByName(String name);

}
