package com.afifa.dto;

import java.time.LocalDate;

public class EventDTO {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public EventDTO(Long id, String description, String name, LocalDate date, int availableTickets) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.date = date;
        this.availableTickets = availableTickets;
    }

    private String description;
    private LocalDate date;
    private int availableTickets;
}
