package hello.core.sigleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonService {

    /**
     * 1. static 영역에 객체를 딱 1개만 생성
     */
    private static final SingletonService instance = new SingletonService();
    // 자기 자신을 static으로 -> class 레벨에 존재해서 하나만 존재한다

    public static SingletonService getInstance() {
        return instance;
    }

    /**
     * 2. public으로 열어서 객체 인스턴스가 필요하면 이 static 메소드를 통해서만 조회하도록 허용
     */
    private SingletonService() { /** 객체 생성 원천 봉쇄 -> 외부에서 생성 못함 */ }

    /**
     * 3. 생성자를 private로 선언, 외부에서 new 키워드를 사용한 객체 생성 방지
     */
    public void singletonLogic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

    // last line
}
