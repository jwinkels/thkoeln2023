package com.thkoeln.referee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thkoeln.referee.models.Event;
import com.thkoeln.referee.repositories.EventRepository;
import com.thkoeln.referee.services.EventService;

@RestController
public class EventController {

    private final EventService service;
    private final EventRepository repository;

    EventController(EventService service, EventRepository repository){
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/event")
    public List<Event> getEvents(@RequestParam(name="home", required = false) String home){
        if(home!=null){
            return repository.findByHome(home);
        }else{
            return repository.findAll();
        }
    }

    @GetMapping("/events/noref")
    public List<Event> getEvents(){
        return repository.findByRefIsNull();
    }

    @PostMapping("/event")
    public Map<String, Object> addEvent(@RequestBody Event event){
        return service.saveEvent(event);
    }

    @PutMapping("/event")
    public Event addReferee(@RequestBody Event event){
        if(service.updateReferee(event)){
            return event;
        }else{
            return null;
        }
    }

    @PutMapping("/event/{id}/scored")
    public Map<String, Object> updateEvent(@PathVariable("id") long id, @RequestBody Map<String, Object> score){
        Optional<Event> event = repository.findById(id);
        Map<String, Object> response = new HashMap<String, Object>();

        if(score.containsKey("team")){
            if(service.updateScore(event.get(), score.get("team").toString())){
                response.put("event", repository.findById(id).get());
                return response;
            }else{
                response.put("error", "Event does not have started!");
                return response;
            }
        }else{
            return null;
        }

    }
}
