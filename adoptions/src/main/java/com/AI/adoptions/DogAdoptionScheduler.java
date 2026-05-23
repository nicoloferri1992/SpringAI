package com.AI.adoptions;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class DogAdoptionScheduler {

    private final DogRepository repository;

    DogAdoptionScheduler(DogRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedDelay = 60_000)
    void checkAdoptions() {
        repository.findAll().forEach(dog -> {
            if (dog.owner() != null) {
                System.out.println("Dog " + dog.name() + " has been adopted by " + dog.owner());
            }
        });
    }
}
