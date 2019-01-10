import cn.sanleny.spring.webflux.webSocket.WebConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.TomcatWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author: sanleny
 * @Date: 2019-01-10
 * @Description: cn.sanleny.spring.webflux.webSocket
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WebConfig.class)
public class MyTests {

    private WebSocketClient client = new ReactorNettyWebSocketClient();

    private WebSocketClient client1=new TomcatWebSocketClient();

    @Before
    public void setUp() throws URISyntaxException {
        URI url = new URI("ws://localhost:8080/path");
        client.execute(url, session ->
                session.receive()
                        .doOnNext(System.out::println)
                        .then());
    }

}
