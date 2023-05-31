package com.thkoeln.referee.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.thkoeln.referee.models.Event;
import com.thkoeln.referee.repositories.EventRepository;

@Service
public class EventService {

    private final EventRepository repository;


    EventService(EventRepository repository){
        this.repository = repository;
    }

    public Map<String, Object> saveEvent(Event event){
        Map<String, Object> response = new HashMap<String, Object>();
        if( event.getHome() != null && event.getGuest() != null && event.getKickoff() != null ){
            Event e = new Event( event.getHome(), event.getGuest(), null, event.getKickoff() );
            repository.save(e);
            response.put("event", e);
        }else{
            response.put("event", event);
            response.put("error", "Home-Team, Guest-Team and Kickofftime must be set!");
        }
        return response;
    }

    public boolean updateReferee(Event event){
        if(event.hasReferee()){
            repository.save(event);
            return true;
        }else{
            return false;
        }
    }

    public boolean updateScore(Event event, String team){
        if( LocalDateTime.now().isAfter( event.getKickoff() ) ){
            event.setScore(team);
            repository.save(event);
            return true;
        }else{
            return false;
        }
    }
}
