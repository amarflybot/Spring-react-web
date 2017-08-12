package com.example.Springreactweb.repo;

import com.example.Springreactweb.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by amarendra on 12/08/17.
 */
public interface PersonRepository {

    Flux<Person> findAll();

    Mono<Person> findById(Integer id);

    Mono<Void> save(Mono<Person> person);
}
