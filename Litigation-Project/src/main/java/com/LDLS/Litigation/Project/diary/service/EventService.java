package com.LDLS.Litigation.Project.diary.service;

import com.LDLS.Litigation.Project.diary.exception.ResourceNotFoundException;
import com.LDLS.Litigation.Project.diary.model.Event;
import com.LDLS.Litigation.Project.diary.repository.EventRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public EventService() {
    }

    public Event createEvent(Event event) {
        return (Event)this.eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return this.eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return (Event)this.eventRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException("Event not found");
        });
    }

    public Event updateEvent(Long id, Event eventDetails) {
        Event event = this.getEventById(id);
        event.setDate(eventDetails.getDate());
        event.setTime(eventDetails.getTime());
        event.setVenue(eventDetails.getVenue());
        event.setShortTitle(eventDetails.getShortTitle());
        event.setDescription(eventDetails.getDescription());
        return (Event)this.eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        Event event = this.getEventById(id);
        this.eventRepository.delete(event);
    }

    public List<Event> findUpcomingEvents(LocalDate today) {
        return this.eventRepository.findAllByEventDateGreaterThanEqual(today);
    }
}
