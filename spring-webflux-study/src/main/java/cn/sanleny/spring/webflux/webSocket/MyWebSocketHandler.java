package cn.sanleny.spring.webflux.webSocket;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Author: sanleny
 * @Date: 2019-01-10
 * @Description: cn.sanleny.spring.webflux.webSocket
 * @Version: 1.0
 */
public class MyWebSocketHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
//        return this.hadle1(webSocketSession);
//        return this.hadle2(webSocketSession);
        return this.hadle3(webSocketSession);


    }

    /**
     * The most basic implementation of a handler is one that handles the inbound stream
     * @param webSocketSession
     * @return
     */
    private Mono<Void> hadle1(WebSocketSession webSocketSession) {
        return webSocketSession.receive()//访问入站消息流
                .doOnNext(message -> {
                    System.out.println(message.toString());
                    //	Do something with each message.
                })
                .concatMap(message -> {
                    //Perform nested asynchronous operations that use the message content.
                    return null;
                })
                .then();//	Return a Mono<Void> that completes when receiving completes.
    }

    /**
     * implementation combines the inbound and outbound streams
     * @param webSocketSession
     * @return
     */
    private Mono<Void> hadle2(WebSocketSession webSocketSession) {
        Flux<WebSocketMessage> output = webSocketSession.receive()  ////Handle the inbound message stream.
                .doOnNext(message -> {
                    // ...
                })
                .concatMap(message -> {
                    // ...
                    return null;
                })
                .map(value -> webSocketSession.textMessage("Echo " + value)); //Create the outbound message, producing a combined flow.

        return webSocketSession.send(output);

    }

    /**
     * Inbound and outbound streams can be independent and be joined only for completion
     * @param webSocketSession
     * @return
     */
    private Mono<Void> hadle3(WebSocketSession webSocketSession) {
        Mono<Void> input = webSocketSession.receive()  //Handle inbound message stream.
                .doOnNext(message -> {
                    //	Do something with each message.
                })
                .concatMap(message -> {
                    //Perform nested asynchronous operations that use the message content.
                    return null;
                })
                .then();

        Flux<String> source =  webSocketSession.receive().doOnNext(message -> {
            // ...
        }).concatMap(message -> {
            // ...
            return null;
        });

        Mono<Void> output = webSocketSession.send(source.map(webSocketSession::textMessage));//	Send outgoing messages.

        return Mono.zip(input, output).then(); //Join the streams and return a Mono<Void> that completes when either stream ends.

    }
}
