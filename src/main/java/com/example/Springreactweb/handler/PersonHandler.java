package com.example.Springreactweb.handler;

import com.example.Springreactweb.model.Person;
import com.example.Springreactweb.repo.PersonRepository;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Created by amarendra on 12/08/17.
 */
public class PersonHandler {
    private final PersonRepository personRepository;

    public PersonHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Mono<ServerResponse> allPerson(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(personRepository.findAll(), Person.class);
    }

    public Mono<ServerResponse> savePerson(ServerRequest request){
        Mono<Person> personMono = request.bodyToMono(Person.class);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(personRepository.findAll(), Person.class);
    }

    public Mono<ServerResponse> getPerson(ServerRequest request){
        Integer id = Integer.valueOf(request.pathVariable("id"));
        Mono<Person> personMono = personRepository.findById(id);
        return personMono.flatMap(person -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .syncBody(person))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
