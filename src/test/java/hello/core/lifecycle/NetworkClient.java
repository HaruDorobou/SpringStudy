package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("init conn msg");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() {
        System.out.println("connect : " + url);
    }

    // service 시작시 호출
    public void call(String msg) {
        System.out.println("call : " + url + " / msg : " + msg);
    }

    // service 종료시 호출
    public void disconnect() {
        System.out.println("close : " + url);
    }
    
    // 이 방법이 제일 편함
    @PostConstruct
    public void init() throws Exception {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 msg");
    }

    @PreDestroy
    public void close() throws Exception {
        System.out.println("NetworkClient.close");
        disconnect();
    }

    // end line
}
