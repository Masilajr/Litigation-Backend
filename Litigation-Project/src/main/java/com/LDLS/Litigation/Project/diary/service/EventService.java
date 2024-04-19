package com.LDLS.Litigation.Project.diary.service;

import com.LDLS.Litigation.Project.diary.exception.ResourceNotFoundException;
import com.LDLS.Litigation.Project.diary.model.EventStatus;
import com.LDLS.Litigation.Project.diary.model.Events;
import com.LDLS.Litigation.Project.diary.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }



        public Events createEvent(Events events) {
            events.setStatus("Pending");

            // Save event to database or perform other operations
            return eventRepository.save(events);
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
    public long countEventsByStatus(EventStatus status) {
        LocalDate today = LocalDate.now();
        switch (status) {
            case UPCOMING:
                return eventRepository.countByEventDateGreaterThanEqualAndCancelledFalse(today);
            case CANCELLED:
                return eventRepository.countByCancelledTrue();
            case COMPLETED:
                return eventRepository.countByEventDateLessThanEqualAndCancelledFalse(today);
            default:
                throw new IllegalArgumentException("Invalid event status: " + status);
        }
    }
    public void updateEventStatus(Events events, boolean isCompleted) {
        LocalDate currentDate = LocalDate.now();
        LocalDate eventDate = events.getEventDate();

        if (isCompleted) {
            events.setStatus("Completed"); // If the frontend marks it as completed
        } else if (eventDate.isEqual(currentDate)) {
            events.setStatus("active"); // If the event is happening today
        } else if (eventDate.isBefore(currentDate)) {
            events.setStatus("Overdue"); // If the event date has passed
        } else {
            events.setStatus("Upcoming"); // If the event date is in the future
        }
        eventRepository.save(events); // Save updated event
    }


    public void cancelEvent(Events events) {
        events.setStatus("Cancelled");
        eventRepository.save(events); // Save updated event
    }

    public List<Events> getPendingEvents(LocalDate today) {
        return eventRepository.findAllByEventDateGreaterThanEqualAndCancelledFalse(today)
                .stream()
                .filter(event -> event.getEventDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
    }

    public List<Events> getActiveEvents(LocalDate today) {
        return eventRepository.findAllByEventDateEqualsAndCancelledFalse(today);
    }

    public List<Events> getCompletedEvents(LocalDate today) {
        LocalDate yesterday = today.minusDays(1);
        return eventRepository.findAllByCompletionTimeIsNotNullAndCompletionTimeBefore(yesterday);
    }

//    public List<Events> getCompletedEventsInChronologicalOrder() {
//        return eventRepository.findAllByOrderByEventDateAsc();
//    }

}