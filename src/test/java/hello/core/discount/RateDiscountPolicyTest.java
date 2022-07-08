package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("Vip는 10% 할인 적용")
    void vip_o() {
        //given
        Member memberA = new Member(1L, "memberA", Grade.VIP);

        //when
        int discount = rateDiscountPolicy.discount(memberA, 10000);

        //then
        Assertions.assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("Vip아니면 할인 미적용")
    void vip_x() {
        //given
        Member memberB = new Member(1L, "memberA", Grade.BASIC);

        //when
        int discount = rateDiscountPolicy.discount(memberB, 10000);

        //then
        Assertions.assertThat(discount).isEqualTo(0);
    }
}