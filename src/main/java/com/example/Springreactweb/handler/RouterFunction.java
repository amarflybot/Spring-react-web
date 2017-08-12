package com.example.Springreactweb.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

/**
 * Created by amarendra on 12/08/17.
 */
@FunctionalInterface
public interface RouterFunction {

    Mono<HandlerFunction> route(ServerRequest request);

    default RouterFunction and(RouterFunction other) {
        return request -> {
            return this.route(request)
                    .switchIfEmpty(other.route(request));
        };
    }

}