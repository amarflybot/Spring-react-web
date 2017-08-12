package com.example.Springreactweb;

import com.example.Springreactweb.handler.PersonHandler;
import com.example.Springreactweb.repo.PersonRepository;
import com.example.Springreactweb.repo.PersonRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Created by amarendra on 12/08/17.
 */
@Configuration
@EnableWebFlux
public class RoutingConfiguration {

    @Bean
    public PersonRepository personRepository(){
        return new PersonRepositoryImpl();
    }

    @Bean
    public PersonHandler handler(final PersonRepository personRepository){
        return new PersonHandler(personRepository);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(final PersonHandler personHandler) {
        return nest(path("/person"),
                nest(accept(APPLICATION_JSON),
                        route(GET("/{id}"), personHandler::getPerson)
                                .andRoute(method(HttpMethod.GET), personHandler::allPerson)
                ).andRoute(POST("/").and(contentType(APPLICATION_JSON)), personHandler::savePerson));
    }
}
