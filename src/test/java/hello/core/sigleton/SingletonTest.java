package hello.core.sigleton;

import hello.core.AppConfig;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        
        // 1. 조회 : 호출할 때 마다 객체를 생성?
        MemberService memberService1 = appConfig.memberService();
        
        // 2. 조회 : 호출할 때 마다 객체를 생성?
        MemberService memberService2 = appConfig.memberService();

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        // 호출할 때 마다 서로 다른 객체가 생성됨을 알 수 있다 - JVM 메모리에 계속 객체가 올라갈 것
        // 最悪... 객체를 생성할 때 마다 적어도 4개의 객체가 메모리에 올라가는 꼴

        // memberService1 과 memberService2는 다르냐? (!=)
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
//        new SingletonService(); -> private access compile error!
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        // 같은 객체 사용

        assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
//        AppConfig appConfig = new AppConfig();

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 1. 조회 : 호출할 때 마다 객체를 생성?
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);

        // 2. 조회 : 호출할 때 마다 객체를 생성?
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        
        // memberService1 과 memberService2는 같냐? (==)
        assertThat(memberService1).isSameAs(memberService2);
        
        // Spring Container가 객체를 싱글톤으로 굴리는 것을 확인
    }

    // last line
}
