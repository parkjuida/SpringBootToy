package parkjuida.exercise.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import parkjuida.exercise.domain.Member;
import parkjuida.exercise.repository.MemberRepository;
import parkjuida.exercise.service.MemberService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void join() throws Exception {
        Member member = new Member();
        member.setName("park");

        Long savedId = memberService.join(member);

        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void duplicateMemberException() throws Exception {
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        memberService.join(member1);
        memberService.join(member2);

        fail("Exception should be called.");
    }

}