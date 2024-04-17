package com.LDLS.Litigation.Project.diary.service;

import com.LDLS.Litigation.Project.diary.exception.ResourceNotFoundException;
import com.LDLS.Litigation.Project.diary.model.Events;
import com.LDLS.Litigation.Project.diary.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Events createEvent(Events event) {
        return eventRepository.save(event);
    }

    public List<Events> getAllEvents() {
        return eventRepository.findAll();
    }

    public Events getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
    }

    public Events updateEvent(Long id, Events updatedEvent) {
        Events event = getEventById(id);
        event.setEventDate(updatedEvent.getEventDate());
        event.setTime(updatedEvent.getTime());
        event.setVenue(updatedEvent.getVenue());
        event.setShortTitle(updatedEvent.getShortTitle());
        event.setDescription(updatedEvent.getDescription());
        return eventRepository.save(event);
    }

    public Events cancelEvent(Long id) {
        Events event = getEventById(id);
        event.setCancelled(true);
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Events> searchEventsByTitle(String title) {
        return eventRepository.findAllByShortTitle(title);
    }

    public List<Events> findUpcomingEvents(LocalDate today) {
        List<Events> events = eventRepository.findAllByEventDateGreaterThanEqual(today);
        events.sort(Comparator.comparing(Events::getEventDate).thenComparing(Events::getTime));
        return events;
    }
}