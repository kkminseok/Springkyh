package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//추가점
//SpringBootTest는 스프링 컨테이너와 함께 실행한다.
//Transactional은 DB에 insert등 쿼리를 다 넣고 rollback을 시켜버림. 실제 DB에 반영을 안한다.
//밑의 테스트 경우 중복 검증이 있는데 매 테스트마다 rollback을 안시켜주면 db를 매번 지워줘야하거나 before()등 전처리를 해줘야하는
//불편한 점이 있다. 기존 MemberServiceTest는 단위테스트라고 하고, DB연동 등을 통합테스트라고 한다. 보통의 경우 단위테스트가 더 좋은 테스트이다.

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

    //테스트는 과감하게 한글로 바꿔도 된다. 빌드될 때 포함되지 않음.
    @Autowired MemberService memberService;
    //달라진 점
    @Autowired MemberRepository memberRepository;

    //중복 에러가 뜨면 DB를 비워주자. test용 DB를 구축
    @Test
    //DB에 반영하도록 함.
    //@Commit
    void 회원가입() {
        //given 무언가 주어졌을 때
        Member member = new Member();
        member.setName("spring");
        //when 어떤 상황에서
        Long saveId = memberService.join(member);
        //then 무언가 나와야한다.
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        //중복을 확인하기 위해 Name에 같은 값을 넣어준다.
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);

        //오류를 검출하기 위한 방법. 변수 e에 메시지를 넣어주고 같은지 학인하는 과정
        IllegalStateException e = assertThrows(IllegalStateException.class,()-> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

}