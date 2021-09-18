package net.iyiguo.simplepoker.router;

import net.iyiguo.simplepoker.handler.PokerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RouterFunctions.route;

/**
 * @author leeyee
 * @date 2021/9/16
 */
@Configuration
public class PokerRouters {

    @Bean
    RouterFunction<ServerResponse> pokerApiRouter(PokerHandler handler) {
        return route()
                .path("/api/pokers", builder -> builder
                        .POST("/{pokerId}/cmd", accept(APPLICATION_JSON), handler::command)
                        .POST("/{pokerId}/vote", accept(APPLICATION_JSON), handler::vote)
                )
                .build();
    }

}
