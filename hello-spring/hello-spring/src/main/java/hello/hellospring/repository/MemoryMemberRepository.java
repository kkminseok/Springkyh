package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;// 키값생성 멤버가 새로 저장될수록 값이 1씩 증가합니다.

    @Override
    public Member save(Member member) {
        member.setId(++sequence);// 저장할 때마다 id값을 증가시킵니다
        store.put(member.getId(),member);//member 객체에 저장된 id값과 member객체를 map객체에 넣습니다.
        return member; // 상황에따라 member를 반환합니다.
    }

    @Override
    public Optional<Member> findById(Long id) {
        //ofNullable함수는 안에 감싸고 있는 store.get()함수가 null을 반환할 경우 empty Optional 객체를 생성합니다.
        //그리고 return을 통해 반환됩니다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        //.stream.filter.findAny()는 findFirst()와 달리 순서와 관계없이 먼저 찾아지는 객체를 리턴합니다.
        //.stream.filter()는 함수식 안에있는 요소드들을 새로운 스트림으로 반환하는 Stream API입니다.
        //.stream()는 stream 객체를 말합니다.
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        //ArrayList형태로 Member값들을 넣고 반환합니다.
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}