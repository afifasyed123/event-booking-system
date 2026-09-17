package com.afifa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Event {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
public Event(){

}
    public Event(long id, String name, String description, LocalDate date,int availableTickets) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.availableTickets=availableTickets;
    }

    @Id
    @GeneratedValue
    private long id;
    String name;
    String description;
    LocalDate date;

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    private int availableTickets;
}
