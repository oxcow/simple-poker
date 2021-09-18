package net.iyiguo.simplepoker.router;

import net.iyiguo.simplepoker.handler.SubscriberHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RouterFunctions.route;

/**
 * @author leeyee
 * @date 2021/9/18
 */
@Configuration
public class SubscriberRouters {

    @Bean
    RouterFunction<ServerResponse> subscriberApiRouter(SubscriberHandler handler) {
        return route()
                .path("/api/subscriber", builder -> builder
                        .GET("/{pokerId}/subscribe/{roomNo}", accept(TEXT_EVENT_STREAM), req -> {
                            Long pokerId = Long.valueOf(req.pathVariable("pokerId"));
                            Long roomNo = Long.valueOf(req.pathVariable("roomNo"));
                            String lastEventIdStr = req.headers().firstHeader("Last-Event-ID");
                            return handler.subscriber(pokerId, roomNo, lastEventIdStr);
                        })
                        .POST("/{pokerId}/unsubscribe/{roomNo}", accept(APPLICATION_JSON), req -> {
                            Long pokerId = Long.valueOf(req.pathVariable("pokerId"));
                            Long roomNo = Long.valueOf(req.pathVariable("roomNo"));
                            return handler.unsubscribe(pokerId, roomNo);
                        })
                )
                .build();
    }
}
