package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * Interface에 의존하는 척 하면 Impl이  FixDiscountPolicy에 의존하고 있음
     * DIP 위반
     */

//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    /**
     * Discount Policy를 바꾸려면 Impl에서 이를 바꿔야 한다
     * OCP 위반
      */
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }


}
