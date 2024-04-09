package com.LDLS.Litigation.Project.diary.Controller;

import com.LDLS.Litigation.Project.diary.model.Event;
import com.LDLS.Litigation.Project.diary.service.EventService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("eventControllerDiary")
@RequestMapping({"/api/events/"})
public class EventControllerDiary {
    @Autowired
    private EventService eventService;

    public EventControllerDiary() {
    }

    @PostMapping({"/add"})
    public Event createEvent(@RequestBody Event event) {
        return this.eventService.createEvent(event);
    }

    @GetMapping({"/read"})
    public List<Event> getAllEvents() {
        return this.eventService.getAllEvents();
    }

    @GetMapping({"/get{id}"})
    public Event getEventById(@PathVariable Long id) {
        return this.eventService.getEventById(id);
    }

    @PutMapping({"/update{id}"})
    public Event updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        return this.eventService.updateEvent(id, eventDetails);
    }

    @DeleteMapping({"/delete{id}"})
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        this.eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping({"/upcoming"})
    public List<Event> findUpcomingEntries() {
        LocalDate today = LocalDate.now();
        return this.eventService.findUpcomingEvents(today);
    }
}