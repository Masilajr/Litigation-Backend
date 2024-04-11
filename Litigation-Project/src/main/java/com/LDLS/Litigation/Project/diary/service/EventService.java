
package com.LDLS.Litigation.Project.diary.service;

import com.LDLS.Litigation.Project.diary.exception.ResourceNotFoundException;
import com.LDLS.Litigation.Project.diary.model.Events;
import com.LDLS.Litigation.Project.diary.repository.EventRepository;
import com.google.api.client.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Create a new event
    public Events createEvent(Events event) {
        return eventRepository.save(event);
    }

    // Retrieve all events
    public List<Events> getAllEvents() {
        return eventRepository.findAll();
    }

    // Retrieve an event by its ID
    public Events getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
    }

    // Update an existing event
    public Events updateEvent(Long id, Events eventDetails) {
        Events event = getEventById(id);
        event.setDate(eventDetails.getDate());
        event.setTime(eventDetails.getTime());
        event.setVenue(eventDetails.getVenue());
        event.setShortTitle(eventDetails.getShortTitle());
        event.setDescription(eventDetails.getDescription());
        return eventRepository.save(event);
    }

    // Delete an event by its ID
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Events> findAllByEventDateGreaterThanEqual(LocalDate date) {
        // Assuming you have a method to fetch events from the Google Calendar API
        List<com.google.api.services.calendar.model.Event> googleEvents = fetchEventsFromGoogleCalendar(date);

        // Convert Google Calendar API events to your application's Events model
        List<Events> events = googleEvents.stream()
                .map(googleEvent -> {
                    Events event = new Events();
                    // Convert Google Calendar API event properties to your Events model
                    event.setDate(convertGoogleDateTimeToLocalDate(googleEvent.getStart().getDateTime()));
                    event.setTime(convertGoogleDateTimeToLocalTime(googleEvent.getStart().getDateTime()));
                    event.setVenue(googleEvent.getLocation());
                    event.setShortTitle(googleEvent.getSummary());
                    event.setDescription(googleEvent.getDescription());
                    // Add any other necessary conversions here
                    return event;
                })
                .collect(Collectors.toList());
        return events;
    }

    // Example method to fetch events from the Google Calendar API
    private List<com.google.api.services.calendar.model.Event> fetchEventsFromGoogleCalendar(LocalDate date) {
        // Implement the logic to fetch events from the Google Calendar API
        // This is just a placeholder method
        return new ArrayList<>();
    }


    // Convert Google Calendar API DateTime to LocalDate
    private LocalDate convertGoogleDateTimeToLocalDate(DateTime googleDateTime) {
        Instant instant = Instant.ofEpochMilli(googleDateTime.getValue());
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // Convert Google Calendar API DateTime to LocalTime
    private LocalTime convertGoogleDateTimeToLocalTime(DateTime googleDateTime) {
        Instant instant = Instant.ofEpochMilli(googleDateTime.getValue());
        return instant.atZone(ZoneId.systemDefault()).toLocalTime();
    }


    // Search events by title
    public List<Events> searchEventsByTitle(String title) {
        return eventRepository.findAllByShortTitle(title);
    }
    public List<Events> findUpcomingEvents(LocalDate today) {
        return eventRepository.findAllByEventDateGreaterThanEqual(today);
    }
}
