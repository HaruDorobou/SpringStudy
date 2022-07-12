package hello.core.sigleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    /**
     * SPRING BEAN은 항상 무상태(STATELESS)하게 설계 해야 한다
     * 반
     * 드
     * 시
     *
     * 공유필드는 정말 반드시 주의하고 주의해야한다
     */

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // Thread A : A사용자 10,000원 주문
        int userAprice = statefulService1.order("userA", 10000);

        // Thread B : B사용자 20,000원 주문
        int userBprice = statefulService1.order("userB", 20000);

        // Thread C : 사용자A 주문 금액 조회
//        int price = statefulService1.getPrice();
        System.out.println("price = " + userAprice);

//        assertThat(statefulService1.getPrice()).isEqualTo(10000);
    }

    static class TestConfig {

        @Bean
        StatefulService statefulService() {
            return new StatefulService();
        }
    }

}