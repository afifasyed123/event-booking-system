package com.afifa.service;

import com.afifa.dto.CreateEventRequestDTO;
import com.afifa.dto.EventDTO;
import com.afifa.dto.EventResponseDTO;
import com.afifa.dto.UpdateEventRequestDTO;
import com.afifa.exception.EventNotFoundException;
import com.afifa.model.Event;
import com.afifa.repository.EventRepository;import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventService {
    private final EventRepository repository;
    public EventService(EventRepository repository){
        this.repository=repository;
    }
    public EventResponseDTO createEvent(CreateEventRequestDTO dto){

        Event event = new Event();

        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setDate(dto.getDate());
        event.setAvailableTickets(dto.getAvailableTickets());

        Event savedEvent=  repository.save(event);
        return toResponseDTO(savedEvent);
    }
    public Page<EventResponseDTO> getAllEvents(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Event> events = repository.findAll(pageable);

        return events.map(this::toResponseDTO);
    }
    public EventResponseDTO updateEvent(Long id, UpdateEventRequestDTO dto) {

        Event event = repository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setDate(dto.getDate());
        event.setAvailableTickets(dto.getAvailableTickets());

        Event updatedEvent = repository.save(event);

        return toResponseDTO(updatedEvent);
    }
    public void deleteEvent(Long id){
        repository.deleteById(id);
    }
    public EventResponseDTO getEventById(Long id) {

        Event event = repository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
    return toResponseDTO(event);
    }
    private EventResponseDTO toResponseDTO(Event event) {
        EventResponseDTO dto = new EventResponseDTO();

        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setDate(event.getDate());
        dto.setAvailableTickets(event.getAvailableTickets());

        return dto;
    }
}
