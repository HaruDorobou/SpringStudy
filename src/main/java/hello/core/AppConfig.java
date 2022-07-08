package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.*;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {

    /**
     * App의 환경구성을 담당하는 Class
     * 객체 형태를 정하고 책임진다
     * 각 구현체들은 AppConfig로부터 주입받은 객체를 실행만하는 역할만한다
     */

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
        // 구현 클래스의 객체에 저장소 Type을 할당하여 전달해준다(이 경우 메모리 저장소로)
        // 구현 클래스는 이로인해 Interface에만 의존할수 있다
        // 이를 생성자 주입이라 한다
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();

        /**
         * Discount Policy를 바꿀때는 영역만 바꾸면 끝
         * Fix -> Rate
         */
    }


    // last line
}
