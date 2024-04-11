package com.LDLS.Litigation.Project.diary.Controller;

import com.LDLS.Litigation.Project.diary.model.Events;
import com.LDLS.Litigation.Project.diary.service.EventService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("eventControllerDiary")
@RequestMapping({"/api/events/"})
public class EventControllerDiary {
    @Autowired
    private EventService eventService;

    public EventControllerDiary() {
    }

    @PostMapping({"/add"})
    public ResponseEntity<Events> createEvent(@RequestBody Events event) {
        Events createdEvent = this.eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping({"/read"})
    public ResponseEntity<List<Events>> getAllEvents() {
        List<Events> events = this.eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping({"/get{id}"})
    public ResponseEntity<Events> getEventById(@PathVariable Long id) {
        Events event = this.eventService.getEventById(id);
        return event != null ? new ResponseEntity<>(event, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping({"/update{id}"})
    public ResponseEntity<Events> updateEvent(@PathVariable Long id, @RequestBody Events eventDetails) {
        Events updatedEvent = this.eventService.updateEvent(id, eventDetails);
        return updatedEvent != null ? new ResponseEntity<>(updatedEvent, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping({"/delete{id}"})
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        this.eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping({"/upcoming"})
    public ResponseEntity<List<Events>> findUpcomingEntries() {
        LocalDate today = LocalDate.now();
        List<Events> upcomingEvents = this.eventService.findUpcomingEvents(today);
        return new ResponseEntity<>(upcomingEvents, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Events>> searchEventsByTitle(@RequestParam String title) {
        List<Events> events = eventService.searchEventsByTitle(title);
        return ResponseEntity.ok(events);
    }
}
