package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

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

    /**
     * Interface에만 의존하게 만들기 DIP 성립
     * 이 경우 NullPointerException 뜸 - 구현체가 없기 때문
     * 누군가 DiscountPolicy의 구현 객체를 대신 생성하고 주입해야함
     * 
     * => AppConfig 도입
      */

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
    // Last Line
}
