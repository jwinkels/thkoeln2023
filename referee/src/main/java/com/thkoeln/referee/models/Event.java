package com.thkoeln.referee.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import com.fasterxml.jackson.annotation.JsonFormat;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="EVENT")
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "number(19,0) default on null event_seq.nextval")
    private Long id;
    private String home;
    private String guest;

    @Column(columnDefinition = "integer default 0")
    private Integer scoreHome;
    @Column(columnDefinition = "integer default 0")
    private Integer scoreGuest;

    @Nullable
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime kickoff;

    @ManyToOne
    @JoinColumn(name="ref_id", nullable=true)
    private Referee ref;

    protected Event(){}

    public Event(String home, String guest, Referee ref, LocalDateTime kickoff){
        this.home       = home;
        this.guest      = guest;
        this.scoreHome  = 0;
        this.scoreGuest = 0;
        this.ref        = ref;
        this.kickoff    = kickoff;
    }

    public Long getId(){
        return this.id;
    }

    public String getHome(){
        return this.home;
    }

    public Integer getScoreHome(){
        return scoreHome;
    }

    public Integer getScoreGuest(){
        return scoreGuest;
    }

    public LocalDateTime getKickoff(){
        return this.kickoff;
    }

    public String getGuest(){
        return this.guest;
    }

    public void setScore(String team){
        if( team.equals(home) ){
            scoreHome += 1;
        }else{
            scoreGuest += 1;
        }
    }

    public String getScore(){
        return String.format("%s %s:%s %s", home, scoreHome, scoreGuest, guest);
    }

    public String getEventInfo(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm");
        if( this.kickoff != null ){
            return String.format("Kickoff: %s, Referee: %s", this.kickoff.format(formatter), ref);
        }else{
            return String.format("Kickoff: undefined, Referee: undefined");
        }
    }

    public String toString(){
        return String.format("%s\n%s", this.getScore(), this.getEventInfo());
    }

    public Referee getRef(){
        return this.ref;
    }

    public boolean hasReferee(){
        if ( this.ref != null ){
            return true;
        }else{
            return false;
        }
    }

}
