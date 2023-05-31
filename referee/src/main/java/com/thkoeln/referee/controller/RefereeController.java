package com.thkoeln.referee.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.thkoeln.referee.models.Referee;
import com.thkoeln.referee.repositories.RefereeRepository;

@RestController
public class RefereeController {

    //Dependency Injection
    private final RefereeRepository repository;

    //Dependency Injection
    RefereeController(RefereeRepository repository){
        this.repository = repository;
    }

    @GetMapping("/referees")
    List<Referee> findReferee(@RequestParam(name="firstName", required = false) String firstName, @RequestParam(name="lastName", required = false) String lastName){
        if(firstName != null && lastName != null ){
            return repository.findByLastNameOrFirstName(lastName, firstName);
        }

        if ( firstName != null ){
            return repository.findByFirstNameLike(firstName);
        }

        if ( lastName != null ){
            return repository.findByLastNameContaining(lastName);
        }

        return repository.findAll();
    }

















    @PostMapping("/referee")
    void newRef(@RequestBody Referee ref) {
        repository.saveReferee(ref.getFirstName(), ref.getLastName());
    }
}
