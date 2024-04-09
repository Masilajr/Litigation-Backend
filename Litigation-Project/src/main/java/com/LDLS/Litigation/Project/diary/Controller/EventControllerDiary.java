package com.LDLS.Litigation.Project.diary.Controller;

import com.LDLS.Litigation.Project.diary.model.Event;
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
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = this.eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping({"/read"})
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = this.eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping({"/get{id}"})
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = this.eventService.getEventById(id);
        return event != null ? new ResponseEntity<>(event, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping({"/update{id}"})
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        Event updatedEvent = this.eventService.updateEvent(id, eventDetails);
        return updatedEvent != null ? new ResponseEntity<>(updatedEvent, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping({"/delete{id}"})
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        this.eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping({"/upcoming"})
    public ResponseEntity<List<Event>> findUpcomingEntries() {
        LocalDate today = LocalDate.now();
        List<Event> upcomingEvents = this.eventService.findUpcomingEvents(today);
        return new ResponseEntity<>(upcomingEvents, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEventsByTitle(@RequestParam String title) {
        List<Event> events = eventService.searchEventsByTitle(title);
        return ResponseEntity.ok(events);
    }
}
