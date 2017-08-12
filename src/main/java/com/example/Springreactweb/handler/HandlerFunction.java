package com.example.Springreactweb.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Created by amarendra on 12/08/17.
 */
@FunctionalInterface
public interface HandlerFunction {

    Mono<ServerResponse> handle(ServerRequest request);

}
