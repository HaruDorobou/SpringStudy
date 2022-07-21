package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        System.out.println("prototypeBean1 = " + prototypeBean1);
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        System.out.println("prototypeBean2 = " + prototypeBean2);
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }

        // end line
    }

    @Test
    void singletonClientUserPrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int cnt1 = clientBean1.logic();
        assertThat(cnt1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int cnt2 = clientBean2.logic();
        assertThat(cnt2).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean {
//        private final PrototypeBean prototypeBean; // 생성시점에 주입

//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        /**
         * Dependency Lookup
         * ObjectProvider를 통해 Bean을 찾아 주입함
         * From where? 스프링 컨테이너를 직접 뒤져서
         */

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        public int logic() {
//            PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);

            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            // javax.inject provider의 get() 메소드 호출
            
            prototypeBean.addCount();
            return prototypeBean.getCount();

            // 웨 이렇게함?
            // unit test에 용이, ApplicationContext에 비의존적
            // 하지만 Sptring 코드를 그대로 가져다씀 - 스프링에 의존적임
            
            // javax.inject의 provider를 이용하여 해결
        }

        // end line
    }

    // end line
}
