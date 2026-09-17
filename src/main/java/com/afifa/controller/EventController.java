package com.afifa.controller;

import com.afifa.dto.CreateEventRequestDTO;
import com.afifa.dto.EventDTO;
import com.afifa.dto.EventResponseDTO;
import com.afifa.dto.UpdateEventRequestDTO;
import com.afifa.model.Event;
import com.afifa.service.EventService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/events")
@SecurityRequirement(name = "bearerAuth")
public class EventController {
 private final EventService service;
    public EventController(EventService service){
        this.service=service;
    }
    @PostMapping
    public EventResponseDTO createEvent(@Valid @RequestBody CreateEventRequestDTO dto){
       return service.createEvent(dto);
    }
    @GetMapping
    public Page<EventResponseDTO> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return service.getAllEvents(page, size, sortBy, direction);
    }
    @GetMapping("/{id}")
    public EventResponseDTO getEventById(@PathVariable Long id) {
        return service.getEventById(id);
    }
    @PutMapping("/{id}")
    public EventResponseDTO updateEvent(
            @PathVariable Long id,
            @RequestBody UpdateEventRequestDTO dto) {

        return service.updateEvent(id, dto);
    }
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id){
         service.deleteEvent(id);
    }
}
