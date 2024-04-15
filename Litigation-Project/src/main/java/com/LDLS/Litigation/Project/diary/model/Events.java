package com.LDLS.Litigation.Project.diary.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;
import lombok.Data;

@Entity
@Data
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "date")
    private LocalDate date;
    @Column(columnDefinition = "time")
    private LocalTime time;
    private String venue;
    private String shortTitle;
    private String description;
    @ManyToOne
    @JoinColumn(name = "case_number")
    private Cases cases;

    public Events() {
    }


    public com.google.api.services.calendar.model.Event toGoogleCalendarEvent() {
        com.google.api.services.calendar.model.Event calendarEvent = new com.google.api.services.calendar.model.Event();
        calendarEvent.setSummary(this.shortTitle);
        calendarEvent.setLocation(this.venue);
        calendarEvent.setDescription(this.description);

        // Convert LocalDate and LocalTime to LocalDateTime
        LocalDateTime startDateTime = this.date.atTime(this.time);
        LocalDateTime endDateTime = startDateTime.plusHours(1); // Example: 1 hour event

        // Set start and end times
        EventDateTime startEventDateTime = new EventDateTime();
        startEventDateTime.setDateTime(new DateTime(startDateTime.toEpochSecond(ZoneOffset.UTC) * 1000));
        startEventDateTime.setTimeZone("America/New_York"); // Adjust the timezone as needed
        calendarEvent.setStart(startEventDateTime);

        EventDateTime endEventDateTime = new EventDateTime();
        endEventDateTime.setDateTime(new DateTime(endDateTime.toEpochSecond(ZoneOffset.UTC) * 1000));
        endEventDateTime.setTimeZone("America/New_York"); // Adjust the timezone as needed
        calendarEvent.setEnd(endEventDateTime);

        return calendarEvent;
    }
}
