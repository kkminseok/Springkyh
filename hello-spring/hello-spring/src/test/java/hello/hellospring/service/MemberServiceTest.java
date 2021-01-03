package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    //테스트는 과감하게 한글로 바꿔도 된다. 빌드될 때 포함되지 않음.
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    //실행하기 전 해줘야할 것들 명세
    @BeforeEach
    public void beforeEach(){
        //객체들을 생성하고 이어주는 과정
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    //밑의 방식은 객체가 유지되지 않는 다른 방법
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    @AfterEach
    public void afterEach(){
        //객체를 지워준다. 안지워주면 테스트순서에따라 객체를 잘못 조회하여 오류가 난다.
        memberRepository.clearStore();
    }




    @Test
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

        /*방법 2 이 방법은 상대적으로 직관적이지 않다.
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
         */
        //then

    }


    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}