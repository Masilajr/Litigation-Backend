package com.LDLS.Litigation.Project.diary.Controller;


import com.LDLS.Litigation.Project.Authentication.Utils.Shared.EntityResponse;
import com.LDLS.Litigation.Project.diary.model.Events;
import com.LDLS.Litigation.Project.diary.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController("eventControllerDiary")
@RequestMapping({"/api/events/"})
public class EventControllerDiary {
    @Autowired
    private EventService eventService;

    public EventControllerDiary() {
    }

    @PostMapping({"/add"})
    public ResponseEntity<EntityResponse> createEvent(@RequestBody Events event) {
        Events createdEvent = this.eventService.createEvent(event);
        EntityResponse response = new EntityResponse();
        response.setMessage("Event created successfully");
        response.setEntity(createdEvent);
        response.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping({"/read"})
    public ResponseEntity<EntityResponse> getAllEvents() {
        List<Events> events = this.eventService.getAllEvents();
        EntityResponse response = new EntityResponse();
        response.setMessage("All events fetched successfully");
        response.setEntity(events);
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping({"/get{id}"})
    public ResponseEntity<EntityResponse> getEventById(@PathVariable Long id) {
        Events event = this.eventService.getEventById(id);
        EntityResponse response = new EntityResponse();
        if (event != null) {
            response.setMessage("Event fetched successfully");
            response.setEntity(event);
            response.setStatusCode(HttpStatus.OK.value());
        } else {
            response.setMessage("Event not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping({"/update{id}"})
    public ResponseEntity<EntityResponse> updateEvent(@PathVariable Long id, @RequestBody Events eventDetails) {
        Events updatedEvent = this.eventService.updateEvent(id, eventDetails);
        EntityResponse response = new EntityResponse();
        if (updatedEvent != null) {
            response.setMessage("Event updated successfully");
            response.setEntity(updatedEvent);
            response.setStatusCode(HttpStatus.OK.value());
        } else {
            response.setMessage("Event not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping({"/delete{id}"})
    public ResponseEntity<EntityResponse> deleteEvent(@PathVariable Long id) {
        this.eventService.deleteEvent(id);
        EntityResponse response = new EntityResponse();
        response.setMessage("Event deleted successfully");
        response.setStatusCode(HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @GetMapping({"/upcoming"})
    public ResponseEntity<EntityResponse> findUpcomingEntries() {
        LocalDate today = LocalDate.now();
        List<Events> upcomingEvents = this.eventService.findUpcomingEvents(today);
        EntityResponse response = new EntityResponse();
        response.setMessage("Upcoming events fetched successfully");
        response.setEntity(upcomingEvents);
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<EntityResponse> searchEventsByTitle(@RequestParam String title) {
        List<Events> events = eventService.searchEventsByTitle(title);
        EntityResponse response = new EntityResponse();
        response.setMessage("Events searched successfully");
        response.setEntity(events);
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

