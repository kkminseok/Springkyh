package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {
    //테스트를 먼저 만드는 것을 테스트 주도개발 ttd
    //테스트 객체생성
    MemoryMemberRepository repository = new MemoryMemberRepository();
    //AfterEach는 콜백함수로 각각 테스트가 끝날때 마다 호출되는 함수를 넣는다.
    @AfterEach
    public void afterEach(){
        //객체를 지워준다. 안지워주면 테스트순서에따라 객체를 잘못 조회하여 오류가 난다.
        repository.clearStore();
    }

    //save()함수 테스트 코드
    @Test
    public void save(){
        //새로운 Member 객체를 만들어준다.
        Member member = new Member();
        member.setName("Spring");

        repository.save(member);

        //result에 찾아온 값을 넣는다.
        Member result = repository.findById(member.getId()).get();//Optional이 반환값이므로
        //밑의 코드들은 전부 result값이 save하기 전 넣었던 값과 같은지 확인하는 코드들이다.
        
        //간단한 방법
        Assertions.assertEquals(member,result);
        //좀 더 간단한 방법
        assertThat(member).isEqualTo(result);
        //오류 검출하는 코드 : Assertions.assertEquals(member,null);
        // 원시적인 방법 : System.out.println("result = " + (result ==member));
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member(); //refactory
        member2.setName("spring1");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring1");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);

    }

}
