package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
//ctrl + shift + t

//jpa쓸 때 필요함.
@Transactional
public class MemberService {
    //기존의 객체를 사용하기 위해 해당 방법을 쓴다.
    private final MemberRepository memberRepositroy;


    public MemberService(MemberRepository memberRepositroy){
        this.memberRepositroy = memberRepositroy;
    }


    /*
    *회원가입 1. 중복 회원 고려
     */
    public Long join(Member member){

            validateDuplicateMember(member);//중복회원 검증 Method refactoring 했음.
            //밑의 함수를 간단하게 위의 메소드로 작성한 것입니다.
        /*
        Optional<Member> result = memberRepositroy.findByName(member.getName());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
         */
            memberRepositroy.save(member);
            return member.getId();



    }

    private void validateDuplicateMember(Member member) {
        memberRepositroy.findByName(member.getName())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    
    /*
    * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepositroy.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepositroy.findById(memberId);
    }

}
