package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core.member",
        // 스캔을 시작할 베이스 경로를 지정함
        // 베이스로부터 쭉 내려가면서 지정함
        // 그 안에 있는 것들만 등록함

        basePackageClasses = AutoAppConfig.class,

        /**
         * Base 지정안하면 어캄?
         * import한 패키지로부터함
         * 여기선 hello.core부터 하겄지
         * 즉, 설정 정보 클래스의 패키지
         */

        /**
         * 그러면 패키지를 지정하지 않고, 설정 정보 클래스의 위치를
         * 프로젝트 최상단에 두면 알아서 다 찾을것
         * 시작루트에 두란 얘기임
         */

        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // 기존에 만들어놨던 @Configuration들을(Test포함) 무시할라고함
        // 수동으로 등록한것들을 죄다 포함하면 충돌나겠지?
)
    /**
     * 보통 실무에선 굳이 이런짓(설정정보 필터링) 안함
     * 그냥 공부니까 기존 설정정보 코드 냄길려고
     */

public class AutoAppConfig {
    // 컴포넌트 스캔 Setting

    /**
     * @ComponentScan은 @Component가 붙은 모든 클래스를
     * 스프링 빈을 등록한다
     *
     * 이때 스프링 빈의 기본 이름은 클래스명을 사용하되,
     * 맨 앞글자만 소문자를 사용한다
     *
     * - Bean 이름 기본 전략 : MemberServiceImpl 클래스 -> memberServiceImpl
     * - Bean 이름 직접 지정 : 만약 스프링 빈의 이름을 직접 지정하고 싶으면?
     * @Component("memberServiceImpl")로 직접 지정하면 Dam
     */

    /**
     * 생성자에 붙는 @Autowired
     * 생성자에 저놈을 지정하면, 스프링 컨테이너가 자동으로
     * 해당 스프링 빈을 찾아서 주입한다
     * 
     * 이 때, 기본 조회 전략은 Type이 같은 빈을 찾아서 주입
     * getBean(MemberRepository.class)와 동일
     * 
     * 생성자에 파라미터가 많아도 다 찾아서 자동으로 착착 주입해줌
     */

    /**
     * @Controller : 스프링 MVC Controller로 인식
     * @Repository : 스프링 데이터 접근 계층으로 인식, 데이터 계층의 예외를 스프링 예외로 변환
     * @Configuration : 스프링 설정 정보로 인식, 스프링 빈이 싱글톤을 유지하도록 추가 처리
     * @Service : 사실 특별한 처리를 하진 앟음 근데 개발자들이 핵심 비즈니스 로직이
     * 여기에 있겠구나~ 라고 비즈니스 계층을 인식하는데 도움을 준다
     */

    // last line
}
