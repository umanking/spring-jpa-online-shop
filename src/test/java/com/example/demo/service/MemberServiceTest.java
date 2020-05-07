package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


/**
 * @author Geonguk Han
 * @since 2020-05-08
 */

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(false)
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("andrew");

        //when
        Long savedId = memberService.join(member);

        //then
        assertThat(member).isEqualTo(memberRepository.findOne(savedId));
    }

    @Test
    void 중복회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("andrew");

        Member member2 = new Member();
        member2.setName("andrew");

        //when
        memberService.join(member1);
        assertThatIllegalStateException().isThrownBy(() -> memberService.join(member2));

        //then
    }
}