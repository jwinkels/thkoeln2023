package com.thkoeln.referee.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="REFEREE")
public class Referee {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    protected Referee() {}

    public Referee(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName  = lastName;
    }

    public String toString(){
        return String.format("%s, %s", lastName, firstName);
    }

    public Long getId(){
        return this.id;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

}
