package fun.kwok.natserver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class NatserverApplicationTests {


    @Value("${WeChat.appid}")
    private String appid;

    @Test
    void contextLoads() {
    }


    @Test
    void testConfig(){
        System.out.println(appid);
    }

}
