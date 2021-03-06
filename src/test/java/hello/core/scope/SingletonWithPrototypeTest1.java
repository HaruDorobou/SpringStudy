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
//        private final PrototypeBean prototypeBean; // ??????????????? ??????

//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        /**
         * Dependency Lookup
         * ObjectProvider??? ?????? Bean??? ?????? ?????????
         * From where? ????????? ??????????????? ?????? ?????????
         */

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        public int logic() {
//            PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);

            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            // javax.inject provider??? get() ????????? ??????
            
            prototypeBean.addCount();
            return prototypeBean.getCount();

            // ??? ?????????????
            // unit test??? ??????, ApplicationContext??? ????????????
            // ????????? Sptring ????????? ????????? ???????????? - ???????????? ????????????
            
            // javax.inject??? provider??? ???????????? ??????
        }

        // end line
    }

    // end line
}
