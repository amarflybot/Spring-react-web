package com.example.Springreactweb.handler;

import org.springframework.web.reactive.function.server.RequestPredicate;
import reactor.core.publisher.Mono;

/**
 * Created by amarendra on 12/08/17.
 */
public abstract class RouterFunctions{
    static RouterFunction route (RequestPredicate requestPredicate, HandlerFunction handlerFunction){
        return request -> {
            if (requestPredicate.test(request)){
                return Mono.just(handlerFunction);
            } else {
                return Mono.empty();
            }
        };
    }
}