package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
        // AppConfig appConfig = new AppConfig();

        // MemberService memberService = new MemberServiceImpl();
        // AppConfig로 전환

        // MemberService memberService = appConfig.memberService();

        /**
         * Spring 도입
         */

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = ac.getBean("memberService", MemberService.class);

        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);

        Member findMember = memberService.findMember(1L);
        System.out.println("New Member = " + memberA.getName());
        System.out.println("Find Member = " + findMember.getName());
    }
}
