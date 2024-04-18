package com.LDLS.Litigation.Project.diary.Controller;

import com.google.api.services.calendar.model.Event;
import com.LDLS.Litigation.Project.diary.service.GoogleCalendarService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class CalendarController {
    private final GoogleCalendarService googleCalendarService;

    public CalendarController(GoogleCalendarService googleCalendarService) {
        this.googleCalendarService = googleCalendarService;
    }

    @PostMapping("/events")
    public Event createOrUpdateEvent(@RequestBody Event event) {
        try {
            // You would retrieve calendarId from your configuration
            String calendarId = "bridgesmith86@gmail.com";
            return googleCalendarService.createOrUpdateEvent(calendarId, event);
        } catch (IOException | GeneralSecurityException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
            return null;
        }
    }
}
