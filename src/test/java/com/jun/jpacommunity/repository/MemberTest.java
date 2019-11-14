package com.jun.jpacommunity.repository;


import com.jun.jpacommunity.domain.Member;
import com.jun.jpacommunity.domain.MemberRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    @Transactional
    @Commit
    public void testInsert() throws Exception {

        //given
        for (int i = 0; i < 100; i++) {

            Member member = new Member();
            member.setUid("user" + i);
            member.setUpw("pw" + i);
            member.setUname("사용자" + i);
            member.setAge(28);
            member.setEmail("a7900" + i + "@gm.com");

            MemberRole memberRole = new MemberRole();

            if(i <= 80){
                memberRole.setRoleName("BASIC");
            }else if (i <= 90){
                memberRole.setRoleName("MANAGER");
            }else {
                memberRole.setRoleName("ADMIN");
            }

            member.setRoles(Arrays.asList(memberRole));

            //when
            memberRepository.save(member);
        }

     }

     @Test
     @Transactional(readOnly = true)
     public void testRead() throws Exception {

         //given
        List<Member> members = memberRepository.findAll();

        members.stream().forEach(member -> {
            System.out.println(member);
        });


         //when

         //then
      }




}
