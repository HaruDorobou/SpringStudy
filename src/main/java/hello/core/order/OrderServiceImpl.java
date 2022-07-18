package hello.core.order;

import hello.core.annotaiton.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor Lombok이 알아서 생성자를 Init
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * Interface에 의존하는 척 하면서 Impl이  FixDiscountPolicy에 의존하고 있음
     * DIP 위반
     */

//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    /**
     * Discount Policy를 바꾸려면 Impl에서 이를 바꿔야 한다
     * Fix -> Rate
     * OCP 위반
      */

//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    
//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy rateDiscountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = rateDiscountPolicy;
//        // 이 경우 @Autowired가 rateDiscountPolicy로 빈을 주입해줌
//        // 왜냐 component scan 돌렸을때 discountPolicy가 두개였음 이걸 rateDiscuntpolicy로 등록해버린것
//    }

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
        // @Qualifier 사용 예제
    }
    

    /**
     * Interface에만 의존하게 만들기 DIP 성립
     * 이 경우 NullPointerException 뜸 - 구현체가 없기 때문
     * 누군가 DiscountPolicy의 구현 객체를 대신 생성하고 주입해야함
     * 
     * => AppConfig 도입
      */


    // 생성자 객체 '불변' 의존관계
    // setter, getter를 의도적으로 두지 않는다
    // 값 수정을 원천적으로 방지
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    /**
     * IF,생성자가 딱 하나만 있다면?
     * @Autowired 없어도 스프링이 알아서 주입해줌
     */
//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        // 생성자 객체 '필수' 의존관계
//        // 생성자에서 오는 값은 무조건 채워 넣어라
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }
/**      @RequiredArgsConstructor -> Lombok이 대신 만들어줌!!
 *       생성자 코드가 없어진것이냐? 그건 아니고 실제 존재함
 *      깔끔하게 생략해준것
 * */

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // Test 용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    // Last Line
}
