package com.example.Springreactweb.repo;

import com.example.Springreactweb.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by amarendra on 12/08/17.
 */
public class PersonRepositoryImpl implements PersonRepository {

    Map<Integer, Person> personMap = new HashMap<>();

    public PersonRepositoryImpl() {
        this.personMap.put(1,new Person(1,"Amar"));
        this.personMap.put(2,new Person(2,"Vicky"));
    }

    @Override
    public Flux<Person> findAll(){
        return Flux.fromIterable(personMap.values());
    }

    @Override
    public Mono<Person> findById(Integer id) {
        return Mono.just(personMap.get(id));
    }

    @Override
    public Mono<Void> save(Mono<Person> person) {
        return person.doOnNext( person1 -> {
            personMap.put(person1.getId(), person1);
            System.out.println("Person -> "+ person1);
        }).thenEmpty(Mono.empty());
    }
}
