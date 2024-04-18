package com.LDLS.Litigation.Project.diary.Controller;

import com.LDLS.Litigation.Project.Authentication.Utils.Shared.EntityResponse;
import com.LDLS.Litigation.Project.diary.model.EventStatus;
import com.LDLS.Litigation.Project.diary.model.Events;
import com.LDLS.Litigation.Project.diary.repository.EventRepository;
import com.LDLS.Litigation.Project.diary.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController("eventControllerDiary")
@RequestMapping({"/api/events/"})
public class EventControllerDiary {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping("/add")
    public ResponseEntity<EntityResponse> createEvent(@RequestBody Events event) {
        try {
            Events createdEvent = this.eventService.createEvent(event);

            EntityResponse response = new EntityResponse();
            response.setMessage("Event created successfully");
            response.setEntity(createdEvent);
            response.setStatusCode(HttpStatus.CREATED.value());

            log.info("Event created successfully: {}", createdEvent);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error creating event: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/read")
    public ResponseEntity<EntityResponse> getAllEvents() {
        List<Events> events = this.eventService.getAllEvents();
        EntityResponse response = new EntityResponse();
        response.setMessage("All events fetched successfully");
        response.setEntity(events);
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
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

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityResponse> updateEvent(@PathVariable Long id, @RequestBody Events eventDetails) {
        try {
            Events updatedEvent = this.eventService.updateEvent(id, eventDetails);

            EntityResponse response = new EntityResponse();
            response.setMessage("Event updated successfully");
            response.setEntity(updatedEvent);
            response.setStatusCode(HttpStatus.OK.value());

            log.info("Event updated successfully: {}", updatedEvent);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("Error updating event: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<EntityResponse> cancelEvent(@PathVariable Long id) {
        try {
            Events cancelledEvent = this.eventService.cancelEvent(id);

            EntityResponse response = new EntityResponse();
            response.setMessage("Event cancelled successfully");
            response.setEntity(cancelledEvent);
            response.setStatusCode(HttpStatus.OK.value());

            log.info("Event cancelled successfully: {}", cancelledEvent);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("Error cancelling event: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<EntityResponse> deleteEvent(@PathVariable Long id) {
        try {
            this.eventService.deleteEvent(id);

            EntityResponse response = new EntityResponse();
            response.setMessage("Event deleted successfully");
            response.setStatusCode(HttpStatus.NO_CONTENT.value());

            log.info("Event deleted successfully with id: {}", id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception e) {
            log.error("Error deleting event: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/upcoming")
    public List<Events> getUpcomingEvents(@RequestParam(value = "today", required = false) String todayString) {
        LocalDate today = todayString != null ? LocalDate.parse(todayString) : LocalDate.now();
        List<Events> events = eventRepository.findAllByEventDateGreaterThanEqual(today);
        events.sort(Comparator.comparing(Events::getEventDate).thenComparing(Events::getTime));
        return events;
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
    @GetMapping("/counts")
    public ResponseEntity<Map<String, Long>> getEventCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("upcoming", eventService.countEventsByStatus(EventStatus.UPCOMING));
        counts.put("cancelled", eventService.countEventsByStatus(EventStatus.CANCELLED));
        counts.put("completed", eventService.countEventsByStatus(EventStatus.COMPLETED));
        return ResponseEntity.ok(counts);
    }
    @GetMapping("/pending")
    public List<Events> getPendingEvents(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate today) {
        return eventService.getPendingEvents(today);
    }

    @GetMapping("/active")
    public List<Events> getActiveEvents(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate today) {
        return eventService.getActiveEvents(today);
    }

    @GetMapping("/completed")
    public List<Events> getCompletedEvents(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate today) {
        return eventService.getCompletedEvents(today);
    }
    @PutMapping("/{eventId}/updateStatus")
    public ResponseEntity<String> updateEventStatus(@PathVariable("eventId") Long eventId,
                                                    @RequestParam(value = "isCompleted", required = false) Boolean isCompleted) {
        try {
            Events event = eventService.getEventById(eventId);
            if (event == null) {
                return ResponseEntity.notFound().build();
            }

            eventService.updateEventStatus(event, isCompleted != null && isCompleted);

            return ResponseEntity.ok("Event status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating event status: " + e.getMessage());
        }
    }
//    @GetMapping("/completed")
//    public List<Events> getCompletedEventsInChronologicalOrder() {
//        return eventService.getCompletedEventsInChronologicalOrder();
//    }
}

