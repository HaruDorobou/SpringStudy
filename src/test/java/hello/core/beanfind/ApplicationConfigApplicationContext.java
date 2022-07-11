package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationConfigApplicationContext {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("Print All Beans")
    void findAllBeand() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
//            Object bean = ac.getBean(beanDefinitionName);
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

//            System.out.println("name = " + beanDefinitionName + "object = " + bean);

            /**
             * Role ROLE_APPLICATION : 직접 등록한 애플리케이션 빈
             * Role ROLE_INFRASTRUCTUE : 스프링이 내부에서 사용하는 빈
             */

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + " / object " + bean);
            }
        }
    }
    // last line
}
