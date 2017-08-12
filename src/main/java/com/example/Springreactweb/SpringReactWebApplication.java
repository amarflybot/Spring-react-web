package com.example.Springreactweb;

import com.example.Springreactweb.handler.PersonHandler;
import com.example.Springreactweb.repo.PersonRepository;
import com.example.Springreactweb.repo.PersonRepositoryImpl;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

//@SpringBootApplication
public class SpringReactWebApplication {


    public static void main(String[] args) {
        //SpringApplication.run(SpringReactWebApplication.class, args);
        PersonRepository personRepository = new PersonRepositoryImpl();
        PersonHandler personHandler = new PersonHandler(personRepository);

        /*HandlerFunction allPerson = request -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(personRepository.findAll(), Person.class);

        HandlerFunction savePerson = request -> {
            Mono<Person> personMono = request.bodyToMono(Person.class);
            Mono<Void> result = personRepository.save(personMono);
            return ServerResponse.ok().build(result);
        };

        HandlerFunction getPerson = request -> {
            Integer id = Integer.valueOf(request.pathVariable("id"));
            Mono<Person> personMono = personRepository.findById(id);
            return personMono.flatMap(person -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .syncBody(person))
                    .switchIfEmpty(ServerResponse.notFound().build());
        };

        RouterFunction allPersonRoute = request -> {
            if (request.path().equals("/person") &&
                    HttpMethod.GET.equals(request.method())){
                return Mono.just(allPerson);
            } else {
                return Mono.empty();
            }
        };

        RouterFunction savePersonRoute = request -> {
            if (request.path().equals("/person") &&
                    HttpMethod.POST.equals(request.method()) &&
                        MediaType.APPLICATION_JSON.equals(request.headers().asHttpHeaders().getContentType())){
                return Mono.just(savePerson);
            }
            else {
                return Mono.empty();
            }
        };*/

        //RouterFunction allPersonRoute = RouterFunctions.route(path("/person").and(method(HttpMethod.GET)), allPerson);

        RouterFunction allRoutes =
                nest(path("/person"),
                route(method(GET), personHandler::allPerson)
                        .and(route(method(POST)
                                .and(contentType(MediaType.APPLICATION_JSON)), personHandler::savePerson))
                        .and(route(GET("/{id}"), personHandler::getPerson)));

    }
}


